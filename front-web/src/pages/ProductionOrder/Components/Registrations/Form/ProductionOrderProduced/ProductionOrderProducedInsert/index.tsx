import { Product } from 'core/types/Product';
import { ProductionOrder, ProductionOrderProduced, Palletstatus } from 'core/types/ProductionOrder';
import { makePrivateRequest } from 'core/utils/request';
import moment from 'moment';
import { useEffect, useState } from 'react';
import DateTime from 'react-datetime'
import { useForm, Controller } from 'react-hook-form';
import Select from 'react-select';

import './styles.scss';

type Props = {
    onInsertItem: (productionOrderProduced: ProductionOrderProduced) => void;
    productionOrder: ProductionOrder;
}

const ProductionOrderProducedInsert = ({ onInsertItem, productionOrder }: Props) => {
    const [manufacturingDate, setManufacturingDate] = useState(new Date());
    const [isLoadingProducts, setIsLoadingProducts] = useState(false);
    const [products, setProducts] = useState<Product[]>([]);

    const [isLoadingProducedProductStatus, setIsLoadingProducedProductStatus] = useState(false);
    const [producedProductStatus, setProducedProductStatus] = useState<Palletstatus[]>([]);

    const { register, errors, control, handleSubmit } = useForm<ProductionOrderProduced>();

    useEffect(() => {
        setIsLoadingProducts(true)
        makePrivateRequest({ url: `/products?formulationProduct=${productionOrder.formulation.id}` })
            .then((response) => {
                setProducts(response.data)
            })
            .finally(() => setIsLoadingProducts(false))
    }, [productionOrder])

    useEffect(() => {
        setIsLoadingProducedProductStatus(true)
        makePrivateRequest({ url: `/palletstatus?listname=` })
            .then((response) => {
                setProducedProductStatus(response.data)
            })
            .finally(() => setIsLoadingProducedProductStatus(false))
    }, [])

    const onSubmit = (data: ProductionOrderProduced) => {
        const payLoad = {
            ...data,
            productionOrderId:productionOrder.id,
            manufacturingDate: moment(manufacturingDate).format("DD/MM/YYYY HH:mm")
        }
        onInsertItem(payLoad);
    }

    return (
        <div>
            <div className="producedInsert-main">
                <div className="producedInsert-inf">
                    <div className="producedInsert-date">
                        <label className="label-base" >Dt. Produção:</label>
                        <DateTime
                            dateFormat="DD/MM/YYYY"
                            timeFormat="HH:mm"
                            onChange={(e) => setManufacturingDate(moment(e.toString()).toDate())}
                            closeOnSelect={true}
                            locale="pt-br"
                            initialValue={manufacturingDate}
                        />
                    </div>

                    <div className="producedInsert-quantity">
                        <label className="label-base">Quantidade:</label>
                        <input
                            name="quantity"
                            ref={register}
                            type="number"
                            className="input-base"
                        />
                    </div>

                    <div className="producedInsert-quantity">
                        <label className="label-base">Lote:</label>
                        <input
                            name="lote"
                            ref={register}
                            type="text"
                            className="input-base"
                        />
                    </div>
                </div>

                <div className="producedInsert-select">
                    <div className="producedInsert-select-product">
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

                        />
                        {errors.product && (
                            <div className="invalid-feedback d-block">
                                Campo obrigatório
                            </div>
                        )}
                    </div>
                    <div className="producedInsert-select-status">
                        <label className="label-base">Status:</label>
                        <Controller
                            as={Select}
                            name="palletstatus"
                            rules={{ required: true }}
                            control={control}
                            isLoading={isLoadingProducedProductStatus}
                            options={producedProductStatus}
                            getOptionLabel={(option: Palletstatus) => option.name}
                            getOptionValue={(option: Palletstatus) => String(option.id)}
                            classNamePrefix="produced-status-select"
                            placeholder="Status"

                        />
                        {errors.product && (
                            <div className="invalid-feedback d-block">
                                Campo obrigatório
                            </div>
                        )}
                    </div>
                </div>
                <div className="producedInsert-actions">
                    <button
                        className="btn btn-success"
                        onClick={handleSubmit(onSubmit)}
                    >
                        Inserir
                    </button>
                </div>

            </div>
        </div>
    )
}

export default ProductionOrderProducedInsert;
