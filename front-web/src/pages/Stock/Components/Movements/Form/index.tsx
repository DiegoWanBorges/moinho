import { StockMovement } from 'core/types/StockMovement';
import { makePrivateRequest } from 'core/utils/request';
import { useEffect, useState } from 'react';
import { useForm, Controller } from 'react-hook-form';
import Select from 'react-select';
import { useParams } from 'react-router';
import DateTime from 'react-datetime'

import { toISOFormatDate } from 'core/utils/utils';
import moment from 'moment';
import { toast } from 'react-toastify';
import './styles.scss'
import { Product } from 'core/types/Product';
import history from 'core/utils/history';

type ParamsType = {
    stockMovementId: string;
}

const StockMovementForm = () => {
    const { register, handleSubmit, errors, setValue, control } = useForm<StockMovement>();
    const [date, setDate] = useState<Date>();
    const { stockMovementId } = useParams<ParamsType>();
    const [stockType, setStockType] = useState('AJUSTE_ESTOQUE')
    const [entryOut, setEntryOut] = useState('ENTRY')
    const isEditing = stockMovementId !== 'new';
    const [isLoadingProducts, setIsLoadingProducts] = useState(false);
    const [products, setProducts] = useState<Product[]>([]);
    const [quantity, setQuantity] = useState(1);
    const [errorQuantity, setErrorQuantity] = useState(false);
    const [quantityMsg, setQuantityMsg] = useState('');
    const isBlockedTypes = ['PRODUCAO_ENTRADA', 'PRODUCAO_CONSUMO', 'PRODUCAO_RETORNO']

    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/stocks/${stockMovementId}` })
                .then(response => {
                    if (isBlockedTypes.some(item => item === response.data.type)) {
                        history.push('/stock/movements/');
                    }
                    setDate(moment(toISOFormatDate(response.data.date)).toDate())
                    setStockType(response.data.type)
                    setValue('idOrignMovement', response.data.idOrignMovement)
                    setValue('product', response.data.product)

                    if (response.data.entry === 0) {
                        setQuantity(response.data.out)
                        stockType === "AJUSTE_ESTOQUE" && setEntryOut("OUT")
                    } else {
                        setQuantity(response.data.entry)
                        stockType === "AJUSTE_ESTOQUE" && setEntryOut("ENTRY")
                    }
                    setValue('cost', response.data.cost)

                })
        } else {
            setDate(new Date())
        }
        // eslint-disable-next-line
    }, [stockMovementId, isEditing, setValue])

    useEffect(() => {
        setIsLoadingProducts(true)
        makePrivateRequest({ url: `/products?listname=` })
            .then((response) => {
                setProducts(response.data)
            })
            .finally(() => setIsLoadingProducts(false))
    }, [])
    useEffect(() => {
        validQuantity()
        // eslint-disable-next-line
    }, [quantity])

    function validQuantity() {
        if (isNaN(quantity)) {
            setErrorQuantity(true)
            setQuantityMsg("Quantidade invalida")
            return false
        } else {
            if (quantity <= 0) {
                setErrorQuantity(true)
                setQuantityMsg("Quantidade deve ser maior que zero!")
                return false
            }
        }
        setErrorQuantity(false)
        setQuantityMsg("")
        return true
    }

    const onSave = (data: StockMovement) => {
        const typeMove = (stockType === "AJUSTE_ESTOQUE" && entryOut === "ENTRY") || (stockType === "COMPRA") ? 'entry' : 'out'
        let payLoad;

        if (validQuantity() === false) {
            return false
        }


        typeMove === 'entry' ? (
            payLoad = {
                ...data,
                date: moment(date).format("DD/MM/YYYY"),
                type: stockType,
                entry: quantity,
                out: 0,
            }) :
            (payLoad = {
                ...data,
                date: moment(date).format("DD/MM/YYYY"),
                type: stockType,
                out: quantity,
                entry: 0
            })

        makePrivateRequest({
            url: isEditing ? `/stocks/${stockMovementId}` : '/stocks/',
            method: isEditing ? 'PUT' : 'POST',
            data: payLoad
        })
            .then(() => {
                toast.success("Lançamento de estoque salvo com sucesso!")
                history.push('/stock/movements/')
            })
            .catch((error) => {
                toast.error(error.response.data.message);
            })
    }

    const onCancel = () => {
        history.push('/stock/movements/');
    }

    return (
        <div className="stockMovementForm-main">
            <div className="stockMovementForm-row">
                <div className="stockMovementForm-date">
                    <label className="label-base">Dt. Inicial:</label>
                    <DateTime
                        dateFormat="DD/MM/YYYY"
                        timeFormat={false}
                        onChange={(e) => setDate(moment(e.toString()).toDate())}
                        closeOnSelect={true}
                        locale="pt-br"
                        initialValue={date}
                        value={date}
                    />
                </div>

                <div className="stockMovementForm-type">
                    <label className="label-base">Tipo de Movimento: </label>
                    <select
                        name="type"
                        className="stockMovementForm-type-select"
                        value={stockType}
                        onChange={(e) => setStockType(e.target.value)}
                    >
                        <option value="AJUSTE_ESTOQUE">Ajuste de Estoque</option>
                        <option value="COMPRA">Compra</option>
                        <option value="VENDA">Venda</option>
                    </select>
                </div>

                <div className={`${stockType === 'AJUSTE_ESTOQUE' ? 'stockMovementForm-in-out' : 'd-none'}`}>
                    <label className="label-base">Entrada/Saida: </label>
                    <select
                        name="entryOut"
                        className="stockMovementForm-in-out-select"
                        value={entryOut}
                        onChange={(e) => setEntryOut(e.target.value)}
                    >
                        <option value="ENTRY">Entrada</option>
                        <option value="OUT">Saida</option>
                    </select>
                </div>
                <div className="stockMovementForm-id">
                    <label className="label-base">Id. Doc:</label>
                    <input
                        className="input-base"
                        name="idOrignMovement"
                        ref={register}
                    />
                </div>

            </div>
            <div className="stockMovementForm-row">
                <div className="stockMovementForm-select-product">
                    <label className="label-base">Produto:</label>
                    <Controller
                        as={Select}
                        name="product"
                        rules={{ required: true }}
                        control={control}
                        isLoading={isLoadingProducts}
                        options={products}
                        getOptionLabel={(option: Product) => option.name}
                        getOptionValue={(option: Product) => String(option.id)}
                        classNamePrefix="products-select"
                        placeholder="Produtos"

                    />
                    {errors.product && (
                        <div className="invalid-feedback d-block">
                            Campo obrigatório
                        </div>
                    )}
                </div>
                <div className="stockMovementForm-quantity">
                    <label className="label-base">Quantidade:</label>
                    <input
                        name="quantity"
                        className="input-base"
                        type="number"
                        onChange={(e) => setQuantity(parseInt(e.target.value))}
                        value={quantity}
                    />
                    {errorQuantity && (
                        <div className="invalid-feedback d-block">
                            {quantityMsg}
                        </div>
                    )}

                </div>
                <div className="stockMovementForm-cost">
                    <label className="label-base">Custo:</label>
                    <input
                        type="number"
                        className="input-base"
                        name="cost"
                        ref={register({
                            required: "Campo obrigatório",
                            min: { value: 0.001, message: "O valor dever ser maior que zero" },
                        })}
                    />
                    {errors.cost && (
                        <div className="invalid-feedback d-block">
                            {errors.cost.message}
                        </div>
                    )}
                </div>
            </div>
            <div className="stockMovementForm-row">
                <div className="stockMovementForm-actions">
                    <button
                        className="btn btn-outline-danger"
                        onClick={onCancel}
                    >
                        Cancelar
                    </button>
                    <button
                        className="btn btn-primary stockMovementForm-btnSave"
                        onClick={handleSubmit(onSave)}
                    >
                        Salvar
                    </button>


                </div>
            </div>
        </div>
    )
}

export default StockMovementForm;