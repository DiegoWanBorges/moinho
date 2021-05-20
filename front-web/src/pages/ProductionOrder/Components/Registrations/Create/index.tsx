import './styles.scss';
import { useEffect, useState } from 'react';
import Select from 'react-select';
import { useForm, Controller } from 'react-hook-form';
import { ProductionOrderItem } from 'core/types/ProductionOrder';
import { Formulation } from 'core/types/Formulation';
import { makePrivateRequest } from 'core/utils/request';
import ProductionOrderCreateCard from './ProductionOrderCreateCard';
import { toast } from 'react-toastify';
import history from 'core/utils/history';
import DateTime from 'react-datetime'
import moment from 'moment';

type FormData = {
    formulation: Formulation;
    ammount: number;
}

function ProductionOrderCreate() {
    const [startDate, setStartDate] = useState(new Date());
    const [isLoadingFormulation, setIsLoadingFormulation] = useState(false);
    const [formulations, setFormulations] = useState<Formulation[]>();
    const { register, handleSubmit, errors, control } = useForm<FormData>();
    const [productionOrderItems, setpProductionOrderItems] = useState<ProductionOrderItem[]>();

    useEffect(() => {
        setIsLoadingFormulation(true)
        makePrivateRequest({ url: `/formulations?listdescription=` })
            .then(response => {
                setFormulations(response.data)
            })
            .finally(() => setIsLoadingFormulation(false))
    }, [])

    function onCreate(data: FormData) {
        makePrivateRequest({
            url: `/productionorders?formulationId=${data.formulation.id}&ammount=${data.ammount}&persistence=false&startDate=${moment(startDate).format("DD/MM/YYYY HH:mm")}`,
            method: 'POST'
        })
            .then(response => {
                setpProductionOrderItems(response.data.productionOrderItems)
            })
            .finally(() => setIsLoadingFormulation(false))
    }

    function onSave(data: FormData) {
        makePrivateRequest({
            url: `/productionorders?formulationId=${data.formulation.id}&ammount=${data.ammount}&persistence=true&startDate=${moment(startDate).format("DD/MM/YYYY HH:mm")}`,
            method: 'POST'
        })
            .then(response => {
                toast.success("Ordem de Produção cadastrada com sucesso!")
                history.push('/productions/registrations/')
            })
            .catch((error) => {
                toast.error("Falha ao cadastrar ordem de produção:" + error.response.data.message)
            })

    }


    return (
        <form className="production-create-main" >
            <div className="production-create-row">
                <div className="production-create-select">
                    <label className="label-base">Selecione a formulação:</label>

                    <Controller
                        as={Select}
                        name="formulation"
                        rules={{ required: true }}
                        control={control}
                        isLoading={isLoadingFormulation}
                        options={formulations}
                        getOptionLabel={(option: Formulation) => option.description}
                        getOptionValue={(option: Formulation) => String(option.id)}
                        placeholder="Formulação"
                    />
                    {errors.formulation && (
                        <div className="invalid-feedback d-block">
                            Campo obrigatório
                        </div>
                    )}
                </div>
                <div className="production-create-inf">
                    <div className="production-create-initial-date">
                        <label className="label-base">Dt. Inicial:</label>
                        <DateTime
                            dateFormat="DD/MM/YYYY"
                            timeFormat="HH:mm"
                            onChange={(e) => setStartDate(moment(e.toString()).toDate())}
                            closeOnSelect={true}
                            locale="pt-br"
                            initialValue={startDate}
                        />

                    </div>

                    <div className="production-create-amount">
                        <label className="label-base">Informe a Quantidade:</label>
                        <input
                            type="number"
                            name="ammount"
                            ref={register({
                                required: "Campo obrigatório"
                            })}
                            className="input-base"
                        />
                        {errors.ammount && (
                            <div className="invalid-feedback d-block">
                                {errors.ammount.message}
                            </div>
                        )}
                    </div>
                </div>

            </div>
            <div className="production-create-action">
                <button
                    className="btn btn-secondary"
                >
                    Limpar
                </button>

                <button
                    className="btn btn-primary production-create-btn-gerar"
                    onClick={handleSubmit(onCreate)}
                >
                    Gerar
                </button>
                {
                    productionOrderItems && (
                        <button
                            className="btn btn-success production-create-btn-save"
                            onClick={handleSubmit(onSave)}
                        >
                            Salvar
                        </button>
                    )
                }

            </div>
            {
                productionOrderItems && (
                    productionOrderItems.map(item => (
                        <ProductionOrderCreateCard
                            key={item.product.id}
                            productionOrderItem={item}
                        />
                    ))
                )
            }
        </form>
    );
}
export default ProductionOrderCreate;
