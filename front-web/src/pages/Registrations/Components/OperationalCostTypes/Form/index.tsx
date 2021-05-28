import { OperationalCostType } from 'core/types/Payment';
import { makePrivateRequest } from 'core/utils/request';
import { useEffect } from 'react';
import {  useForm } from 'react-hook-form';
import { useHistory, useParams } from 'react-router';
import { toast } from 'react-toastify';
import './styles.scss';


type ParamsType = {
    operationalCostTypeId: string;
}
function OperationalCostTypeForm() {
    const { register, handleSubmit, errors, setValue } = useForm<OperationalCostType>();
    const history = useHistory();
    const { operationalCostTypeId } = useParams<ParamsType>();
    const isEditing = operationalCostTypeId !== 'new';
    
    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/operationalcosttypes/${operationalCostTypeId}` })
                .then(response => {
                    setValue('name', response.data.name);
                    setValue('type', response.data.type);
                })
        }
    }, [operationalCostTypeId, isEditing, setValue])

    const onSubmit = (data: OperationalCostType) => {
        makePrivateRequest({
            url: isEditing ? `/operationalcosttypes/${operationalCostTypeId}` : '/operationalcosttypes/',
            method: isEditing ? 'PUT' : 'POST',
            data: data
        })
            .then(() => {
                toast.success("Tipo de custo operacional salvo com sucesso!")
                history.push('/registrations/operationalcosttypes/')
            })
            .catch(() => {
                toast.error("Erro ao salvar tipo de custo operacional!")
            })
    }

    const handleCancel = ()=>{
        history.push("./")
    }
    return (
        <form onSubmit={handleSubmit(onSubmit)} className="operationalCostTypeCard-form">
          
            <div className="operationalCostTypeCard-form-row">
                <div className="operationalCostTypeCard-form-name">
                    <label className="label-base" >Nome do custo operacional:</label>
                    <input
                        name="name"
                        type="text"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório",
                            minLength: { value: 2, message: "O campo deve ter minímo 2 caracteres" },
                            maxLength: { value: 40, message: "O campo deve ter no maximo 10 caracteres" },
                        })}
                    />
                    {errors.name && (
                        <div className="invalid-feedback d-block">
                            {errors.name.message}
                        </div>
                    )}
                </div>
            </div>
            <div className="operationalCostTypeCard-form-row-item">
                <label className="label-base">Tipo de rateio: </label>
                <select name="type" ref={register} className="operationalCostTypeCard-select">
                    <option value="TEMPO_PRODUCAO">Tempo de produção</option>
                    <option value="VOLUME_MATERIA_PRIMA">Volume Materia Prima</option>
                </select>
            </div>
          
            <div className="operationalCostTypeCard-form-actions">
                <button
                    className="btn btn-outline-danger"
                    onClick={handleCancel}
                >
                    CANCELAR
                </button>
                <button className="btn btn-primary operationalCostTypeCard-form-btn-save">
                    SALVAR
                </button>
            </div>
        </form>
    );
}
export default OperationalCostTypeForm;
