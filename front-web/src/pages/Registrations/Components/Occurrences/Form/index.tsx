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
    occurrenceId: string;
}
function OccurrenceForm() {
    const { register, handleSubmit, errors, setValue } = useForm<FormState>();
    const history = useHistory();
    const { occurrenceId } = useParams<ParamsType>();
    const isEditing = occurrenceId !== 'new';


    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/occurrences/${occurrenceId}` })
                .then(response => {
                    setValue('name', response.data.name);
                })
        }
    }, [occurrenceId, isEditing, setValue])



    const onSubmit = (data: FormState) => {
        makePrivateRequest({
            url: isEditing ? `/occurrences/${occurrenceId}` : '/occurrences/',
            method: isEditing ? 'PUT' : 'POST',
            data: data
        })
            .then(() => {
                toast.success("Ocorrência salva com sucesso!")
                history.push('/registrations/occurrences/')
            })
            .catch(() => {
                toast.error("Erro ao salvar ocorrência!")
            })
    }

    const handleCancel = () => {
        history.push("./") 
    }
    return (
        <form onSubmit={handleSubmit(onSubmit)} className="occurrence-form">
            <div className="occurrence-form-main">

                <div className="occurrence-form-name">
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

                <div className="occurrence-form-actions">
                    <button
                        className="btn btn-outline-danger"
                        onClick={handleCancel}
                    >
                        CANCELAR
                </button>
                    <button className="btn btn-primary occurrence-form-btn-save">
                        SALVAR
                </button>
                </div>
            </div>
        </form>
    );
}
export default OccurrenceForm;
