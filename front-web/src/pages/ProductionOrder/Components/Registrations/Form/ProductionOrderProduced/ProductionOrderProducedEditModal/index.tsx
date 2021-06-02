import Modal from 'react-modal';
import { useEffect, useState } from 'react';
import { useForm, Controller } from 'react-hook-form';
import { Palletstatus, ProductionOrderProduced } from 'core/types/ProductionOrder';
import Select from 'react-select';
import { Product } from 'core/types/Product';
import { makePrivateRequest } from 'core/utils/request';
import DateTime from 'react-datetime'

import './styles.scss';
import moment from 'moment';
import { toISOFormatDateTime } from 'core/utils/utils';
type Props = {
    productionOrderProduced: ProductionOrderProduced;
    onEditItem: (productionOrderProduced: ProductionOrderProduced) => void;
    formulationId: number;
}
function ProductionOrderProducedEditModal({ productionOrderProduced, onEditItem, formulationId }: Props) {
    const { register, errors, handleSubmit, setValue, control } = useForm<ProductionOrderProduced>();
    const [show, setShow] = useState(false);

    const [isLoadingProducts, setIsLoadingProducts] = useState(false);
    const [products, setProducts] = useState<Product[]>([]);

    const [isLoadingProducedProductStatus, setIsLoadingProducedProductStatus] = useState(false);
    const [producedProductStatus, setProducedProductStatus] = useState<Palletstatus[]>([]);

    const [manufacturingDate, setManufacturingDate] = useState<Date>();

    const onEdit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault();
        setShow(!show)
    }
    async function afterOpenModal() {
        setIsLoadingProducts(true)
        await makePrivateRequest({ url: `/products?formulationProduct=${formulationId}` })
            .then((response) => {
                setProducts(response.data)
            })
            .finally(() => setIsLoadingProducts(false))

        setIsLoadingProducedProductStatus(true)
        await makePrivateRequest({ url: `/palletstatus?listname=` })
            .then((response) => {
                setProducedProductStatus(response.data)
            })
            .finally(() => setIsLoadingProducedProductStatus(false))
        
        setManufacturingDate(moment(toISOFormatDateTime(productionOrderProduced.manufacturingDate)).toDate());
        setValue('quantity', productionOrderProduced.quantity)
        setValue('lote', productionOrderProduced.lote)
        setValue('product', productionOrderProduced.product)
        setValue('palletStatus', productionOrderProduced.palletStatus)
    }
    const onSave = (data: ProductionOrderProduced) => {
        const payLoad = {
            ...data,
            manufacturingDate:moment(manufacturingDate).format("DD/MM/YYYY HH:mm"),
            productionOrderId: productionOrderProduced.productionOrderId,
            pallet: productionOrderProduced.pallet
        }
        console.log(payLoad)
        onEditItem(payLoad);
        setShow(!show);
    }
    useEffect(() => {

    }, [])




    return (
        <>
            <button
                className="btn btn-primary btn-sm producedModal-edit"
                onClick={e => onEdit(e)}
            >
                Editar
            </button>

            <Modal
                isOpen={show}
                onAfterOpen={afterOpenModal}
                ariaHideApp={false}
                className="producedModal-main"
                overlayClassName="producedModal-overlay"
            >
                <h5>{productionOrderProduced.product.name} - Pallet:{productionOrderProduced.pallet}</h5>

                <div className="producedModal-row">
                    <div className="producedModal-manufacturingDate">
                        <label className="label-base">Dt. Inicial:</label>
                        <DateTime
                            dateFormat="DD/MM/YYYY"
                            timeFormat="HH:mm"
                            onChange={(e) => setManufacturingDate(moment(e.toString()).toDate())}
                            closeOnSelect={true}
                            locale="pt-br"
                            initialValue={manufacturingDate}
                            value={manufacturingDate}

                        />
                    </div>
                </div>

                <div className="producedModal-row">
                    <div className="producedModal-quantity">
                        <label className="label-base">Quantidade:</label>
                        <input
                            className="input-base"
                            name="quantity"
                            type="number"
                            ref={register({
                                required: "Campo obrigatório",
                                min: { value: 0.001, message: "O valor dever ser maior que zero" },
                            })}
                        />
                        {errors.quantity && (
                            <div className="invalid-feedback d-block">
                                {errors.quantity.message}
                            </div>
                        )}
                    </div>
                    <div className="producedModal-lote">
                        <label className="label-base">Lote:</label>
                        <input
                            className="input-base"
                            name="lote"
                            type="text"
                            ref={register({
                                required: "Campo obrigatório"
                            })}
                        />
                        {errors.quantity && (
                            <div className="invalid-feedback d-block">
                                {errors.quantity.message}
                            </div>
                        )}
                    </div>

                </div>

                <div className="producedModal-select-product">
                    <label className="label-base">Produto Acabado:</label>
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
                        className="select-teste"
                    />
                    {errors.product && (
                        <div className="invalid-feedback d-block">
                            Campo obrigatório
                        </div>
                    )}
                </div>
                <div className="producedModal-select-product">
                    <label className="label-base">Status:</label>
                    <Controller
                        as={Select}
                        name="palletStatus"
                        rules={{ required: true }}
                        control={control}
                        isLoading={isLoadingProducedProductStatus}
                        options={producedProductStatus}
                        getOptionLabel={(option: Palletstatus) => option.name}
                        getOptionValue={(option: Palletstatus) => String(option.id)}
                        classNamePrefix="palletStatus-select"
                        placeholder="Status"
                        className="select-teste"
                    />
                    {errors.product && (
                        <div className="invalid-feedback d-block">
                            Campo obrigatório
                        </div>
                    )}
                </div>
                <div>

                    <div className="producedModal-btn">
                        <button
                            className="btn btn-primary producedModal-btn-save"
                            onClick={handleSubmit(onSave)}                    >
                            Salvar
                    </button>

                        <button
                            className="btn btn-danger producedModal-btn-del"
                            onClick={() => setShow(!show)}
                        >
                            Cancelar
                    </button>
                    </div>
                </div>
            </Modal>
        </>
    );
}
export default ProductionOrderProducedEditModal;
