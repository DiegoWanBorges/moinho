import { FormulationItem } from 'core/types/Formulation'
import { useForm, Controller } from 'react-hook-form';
import Select from 'react-select';

import './styles.scss';
import { useEffect, useRef, useState } from 'react';
import { makePrivateRequest } from 'core/utils/request';
import { Product } from 'core/types/Product';
import { ProductionOrder, ProductionOrderItem } from 'core/types/ProductionOrder';

type Props = {
    onInsertItem: (productionOrderItem: ProductionOrderItem) => void;
    productionOrder:ProductionOrder;
}


function ProductionOrderItemsInsert({ onInsertItem, productionOrder }: Props) {
    const { register, errors, control, handleSubmit, setValue } = useForm<ProductionOrderItem>();
    const [isLoadingProducts, setIsLoadingProducts] = useState(false);
    const [products,setProducts]=useState<Product[]>([]);
    const [type,setType] = useState('')
  
    useEffect(() =>{
        setValue('product','');
        setIsLoadingProducts(true)
        var prod =  productionOrder.formulation.formulationItems.filter(function(pro) {
            return pro.type === (type !== 'EXTRA' ? 'NORMAL' : 'EXTRA');
        });
        setProducts(prod.map(pp =>(
            pp.product
        )));
        setIsLoadingProducts(false)
    },[type,productionOrder])
    
    const onSubmit = (data: ProductionOrderItem) => {
        const payload ={
            ...data,
            productionOrderId:productionOrder.id,
        }
        onInsertItem(payload)
    }
    return (
        <div className="formulationItem-main">
            <div className="formulationItem-row">
                <div className="formulationItem-round">
                    <label className="label-base">Tipo? </label>
                    
                    <div  className="parameter-content-withou-stock-" >
                        <input
                            ref={register}
                            type="radio"
                            value="COMPLEMENTAR"
                            name="type"
                            onChange={(e) => setType(e.target.value)}
                        /> Complementar
                        
                        <input
                            type="radio"
                            ref={register}
                            value="RETORNO"
                            name="type"
                            onChange={(e) => setType(e.target.value)}
                        /> Retorno
                        <input
                            type="radio"
                            value="EXTRA"
                            ref={register}
                            name="type"
                            onChange={(e) => setType(e.target.value)}
                        /> Extra
                    </div>
                </div>
              
                <div className="formulationItem-select">
                    <label className="label-base">Ingrediente:</label>
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

                <div className="formulationItem-quantity">
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



                <div className="formulationItem-insert">

                    <button
                        className="btn btn-success formulationItem-btn"
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
