import { makePrivateRequest } from 'core/utils/request';
import { useEffect } from 'react';
import {  useForm } from 'react-hook-form';
import { useHistory, useParams } from 'react-router';
import { toast } from 'react-toastify';
import './styles.scss';

export type FormState = {
    name: string;
}
type ParamsType = {
    laborCostTypeId: string;
}
function LaborCosTypeForm() {
    const { register, handleSubmit, errors, setValue } = useForm<FormState>();
    const history = useHistory();
    const { laborCostTypeId } = useParams<ParamsType>();
    const isEditing = laborCostTypeId !== 'new';


    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/laborcosttypes/${laborCostTypeId}` })
                .then(response => {
                    setValue('name', response.data.name);
                })
        }
    }, [laborCostTypeId, isEditing, setValue])



    const onSubmit = (data: FormState) => {
        makePrivateRequest({
            url: isEditing ? `/laborcosttypes/${laborCostTypeId}` : '/laborcosttypes/',
            method: isEditing ? 'PUT' : 'POST',
            data: data
        })
            .then(() => {
                toast.success("Tipo de pagamento de funcionario salvo com sucesso!")
                history.push('/registrations/laborcosttypes/')
            })
            .catch(() => {
                toast.error("Erro ao salvar tipo de pagamento de funcionario !")
            })
    }

    const handleCancel = () => {
        history.push("./") 
    }
    return (
        <form onSubmit={handleSubmit(onSubmit)} className="laborCosType-form">
            <div className="laborCosType-form-main">

                <div className="laborCosType-form-name">
                    <label className="label-base" >Nome:</label>
                    <input
                        name="name"
                        type="text"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório",
                            minLength: { value: 2, message: "O campo deve ter minímo 2 caracteres" },
                            maxLength: { value: 50, message: "O campo deve ter no maximo 10 caracteres" },
                        })}
                    />
                    {errors.name && (
                        <div className="invalid-feedback d-block">
                            {errors.name.message}
                        </div>
                    )}
                </div>

                <div className="laborCosType-form-actions">
                    <button
                        className="btn btn-outline-danger"
                        onClick={handleCancel}
                    >
                        CANCELAR
                </button>
                    <button className="btn btn-primary laborCosType-form-btn-save">
                        SALVAR
                </button>
                </div>
            </div>
        </form>
    );
}
export default LaborCosTypeForm;
