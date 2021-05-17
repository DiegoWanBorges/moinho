
import { FormulationItem } from 'core/types/Formulation'
import { useForm, Controller } from 'react-hook-form';
import Select from 'react-select';
import { Product } from 'core/types/Product';

import './styles.scss';
import { useEffect, useState } from 'react';
import { makePrivateRequest } from 'core/utils/request';

type Props = {
    onInsertItem: (formulationItem:FormulationItem) =>void;
}

function FormulationItems({ onInsertItem }: Props) {
    const { register, errors, control, handleSubmit,setValue } = useForm<FormulationItem>();
    const [isLoadingProducts, setIsLoadingProducts] = useState(false);
    const [products, setProducts] = useState<Product[]>();

    useEffect(() => {
        setIsLoadingProducts(true);
        makePrivateRequest({ url: `/products?listname=` })
            .then(response => {
                setProducts(response.data)
            })
            .finally(() => setIsLoadingProducts(false))
    }, [])

    useEffect(() => {
        setValue('quantity',1);
        setValue('round','false');
        setValue('rawMaterial','false');
    }, [setValue])

    const onSubmit = (data: FormulationItem) => {
        onInsertItem(data)
    }



    return (
        <div className="formulationItem-main">
            <div className="formulationItem-row">
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

                <div className="formulationItem-round">
                    <label className="label-base">Arredondar? </label>
                    <div className="parameter-content-withou-stock-">
                        <input
                            ref={register}
                            type="radio"
                            value="true"
                            name="round"
                        /> Sim
                    <input
                            ref={register}
                            type="radio"
                            value="false"
                            name="round"
                        /> Não
               </div>
                </div>

                <div className="formulationItem-raw-material">
                    <label className="label-base">Materia-prima? </label>
                    <div className="parameter-content-withou-stock-">
                        <input
                            ref={register}
                            type="radio"
                            value="true"
                            name="rawMaterial"
                        /> Sim
                    <input
                            ref={register}
                            type="radio"
                            value="false"
                            name="rawMaterial"
                        /> Não
               </div>
                </div>

                <div className="formulationItem-type">
                    <label className="label-base">Tipo? </label>
                    <select name="type" ref={register} className="parameter-content-type-cost-select">
                        <option value="NORMAL">Normal</option>
                        <option value="EXTRA">Extra</option>
                    </select>
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
export default FormulationItems;
