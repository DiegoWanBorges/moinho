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
    groupId: string;
}
function GroupForm() {
    const { register, handleSubmit, errors, setValue } = useForm<FormState>();
    const history = useHistory();
    const { groupId } = useParams<ParamsType>();
    const isEditing = groupId !== 'new';


    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/groups/${groupId}` })
                .then(response => {
                    setValue('name', response.data.name);
                })
        }
    }, [groupId, isEditing, setValue])



    const onSubmit = (data: FormState) => {
        makePrivateRequest({
            url: isEditing ? `/groups/${groupId}` : '/groups/',
            method: isEditing ? 'PUT' : 'POST',
            data: data
        })
            .then(() => {
                toast.success("Grupo salvo com sucesso!")
                history.push('/registrations/groups/')
            })
            .catch(() => {
                toast.error("Erro ao salvar grupo!")
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
export default GroupForm;
