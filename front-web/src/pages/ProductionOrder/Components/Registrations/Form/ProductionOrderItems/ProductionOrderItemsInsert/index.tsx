
import { useForm, Controller } from 'react-hook-form';
import Select from 'react-select';

import './styles.scss';
import { useEffect, useState } from 'react';
import { makePrivateRequest } from 'core/utils/request';
import { Product } from 'core/types/Product';
import { Occurrence, ProductionOrder, ProductionOrderItem } from 'core/types/ProductionOrder';

type Props = {
    onInsertItem: (productionOrderItem: ProductionOrderItem) => void;
    productionOrder: ProductionOrder;
}



function ProductionOrderItemsInsert({ onInsertItem, productionOrder }: Props) {
    const { register, errors, control, handleSubmit, setValue } = useForm<ProductionOrderItem>();
    const [isLoadingProducts, setIsLoadingProducts] = useState(false);
    const [products, setProducts] = useState<Product[]>([]);
    const [isLoadingOccurrences, setIsLoadingoccurrences] = useState(false);
    const [occurrences, setOccurrences] = useState<Occurrence[]>([]);
    const [type, setType] = useState('')

    useEffect(() => {
        setValue('product', '');
        setIsLoadingProducts(true)
        var prod = productionOrder.formulation.formulationItems.filter(function (pro) {
            return pro.type === (type !== 'EXTRA' ? 'NORMAL' : 'EXTRA');
        });
        setProducts(prod.map(pp => (
            pp.product

        )));
        setIsLoadingProducts(false)
    }, [type, productionOrder, setValue])


    useEffect(() => {
        setValue('quantity',1);
        setIsLoadingoccurrences(true)
        makePrivateRequest({ url: `/occurrences?name=` })
            .then((response) => {
                setOccurrences(response.data)
            })
            .finally(() => setIsLoadingoccurrences(false))
    }, [setValue])


    const onSubmit = (data: ProductionOrderItem) => {
        var prod = productionOrder.formulation.formulationItems.filter(function (pro) {
            return pro.product.id === data.product.id
        });
        const payload = {
            ...data,
            productionOrderId: productionOrder.id,
            rawMaterial: prod[0].rawMaterial,

        }
        onInsertItem(payload)
    }
    return (
        <div className="ordemProductionItem-main">
            <div className="ordemProductionItem-row">

                <div className="ordemProductionItem-type">
                    <label className="label-base">Tipo? </label>
                    <div className="ordemProductionItem-type-item">
                        <input
                            ref={register({ required: true })}
                            type="radio"
                            value="COMPLEMENTAR"
                            name="type"
                            onChange={(e) => setType(e.target.value)}
                            defaultChecked={true}
                        /> {' Complementar '}

                        <input
                            type="radio"
                            ref={register({ required: true })}
                            value="RETORNO"
                            name="type"
                            onChange={(e) => setType(e.target.value)}
                        /> {' Retorno '}
                        <input
                            type="radio"
                            value="EXTRA"
                            ref={register({ required: true })}
                            name="type"
                            onChange={(e) => setType(e.target.value)}
                        /> {' Extra '}

                        </div>
                </div>

                <div className="ordemProductionItem-select">
                    <label className="label-base">Ingredientes:</label>
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

                <div className="ordemProductionItem-select">
                    <label className="label-base">Ocorrências:</label>
                    <Controller
                        as={Select}
                        name="occurrence"
                        rules={{ required: true }}
                        control={control}
                        isLoading={isLoadingOccurrences}
                        options={occurrences}
                        getOptionLabel={(option: Occurrence) => option.name}
                        getOptionValue={(option: Occurrence) => String(option.id)}
                        classNamePrefix="occurrences-select"
                        placeholder="Ocorrências"

                    />
                    {errors.product && (
                        <div className="invalid-feedback d-block">
                            Campo obrigatório
                        </div>
                    )}
                </div>

                <div className="ordemProductionItem-quantity">
                    <label className="label-base">Quantidade:</label>
                    <div>
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

                </div>

                <div className="ordemProductionItem-insert">
                    <button
                        className="btn btn-success ordemProductionItem-btn"
                        onClick={handleSubmit(onSubmit)}
                    >
                        Inserir
                </button>
                </div>

            </div>
        </div>
    );
}
export default ProductionOrderItemsInsert;
