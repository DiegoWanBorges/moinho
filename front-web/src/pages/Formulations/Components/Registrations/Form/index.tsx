import { makePrivateRequest } from 'core/utils/request';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useHistory, useParams } from 'react-router';
import { toast } from 'react-toastify';
import './styles.scss';

export type FormState = {
    description: string;
}
type ParamsType = {
    formulationId: string;
}
function FormulationForm() {
    const { register, handleSubmit, errors, setValue } = useForm<FormState>();
    const history = useHistory();
    const { formulationId } = useParams<ParamsType>();
    const isEditing = formulationId !== 'new';


    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/formulations/${formulationId}` })
                .then(response => {
                    setValue('description', response.data.description);
                })
        }
    }, [formulationId, isEditing, setValue])



    const onSubmit = (data: FormState) => {
        makePrivateRequest({
            url: isEditing ? `/formulations/${formulationId}` : '/formulations/',
            method: isEditing ? 'PUT' : 'POST',
            data: data
        })
            .then(() => {
                toast.success("Formulação salva com sucesso!")
                history.push('/formulations/registrations/')
            })
            .catch(() => {
                toast.error("Erro ao salvar formulação!")
            })
    }

    const handleCancel = () => {
        history.push("./") 
    }
    return (
        <form onSubmit={handleSubmit(onSubmit)} className="group-form">
            <div className="group-form-main">

                <div className="group-form-name">
                    <label className="label-base" >Nome:</label>
                    <input
                        name="description"
                        type="text"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório",
                            minLength: { value: 2, message: "O campo deve ter minímo 2 caracteres" },
                            maxLength: { value: 50, message: "O campo deve ter no maximo 10 caracteres" },
                        })}
                    />
                    {errors.description && (
                        <div className="invalid-feedback d-block">
                            {errors.description.message}
                        </div>
                    )}
                </div>

                <div className="group-form-actions">
                    <button
                        className="btn btn-outline-danger"
                        onClick={handleCancel}
                    >
                        CANCELAR
                </button>
                    <button className="btn btn-primary group-form-btn-save">
                        SALVAR
                </button>
                </div>
            </div>
        </form>
    );
}
export default FormulationForm;
