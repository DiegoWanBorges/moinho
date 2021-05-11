import { makePrivateRequest } from 'core/utils/request';
import { useEffect } from 'react';
import {  useForm } from 'react-hook-form';
import { useHistory, useParams } from 'react-router';
import { toast } from 'react-toastify';
import './styles.scss';

export type FormState = {
    id:string;
    description: string;
}
type ParamsType = {
    unitId: string;
}
function UnityForm() {
    const { register, handleSubmit, errors, setValue } = useForm<FormState>();
    const history = useHistory();
    const { unitId } = useParams<ParamsType>();
    const isEditing = unitId !== 'new';


    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/units/${unitId}` })
                .then(response => {
                    setValue('id', response.data.id);
                    setValue('description', response.data.description);
                })
        }
    }, [unitId, isEditing, setValue])



    const onSubmit = (data: FormState) => {
        makePrivateRequest({
            url: isEditing ? `/units/${unitId}` : '/units/',
            method: isEditing ? 'PUT' : 'POST',
            data: data
        })
            .then(() => {
                toast.success("Unidade salva com sucesso!")
                history.push('/registrations/units/')
            })
            .catch(() => {
                toast.error("Erro ao salvar unidade!")
            })
    }

    const handleCancel = () => {
        history.push("./") 
    }
    return (
        <form onSubmit={handleSubmit(onSubmit)} className="unity-form">
            <div className="unity-form-main">
            
                <div className="unity-form-name">
                <label className="label-base" >Cógigo:</label>
                    <input
                        name="id"
                        type="text"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório",
                            minLength: { value: 1, message: "O campo deve ter minímo 1 caracteres" },
                            maxLength: { value: 2, message: "O campo deve ter no maximo 2 caracteres" },
                        })}
                    />
                    {errors.id && (
                        <div className="invalid-feedback d-block">
                            {errors.id.message}
                        </div>
                    )}
                    
                    
                    
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

                <div className="unity-form-actions">
                    <button
                        className="btn btn-outline-danger"
                        onClick={handleCancel}
                    >
                        CANCELAR
                </button>
                    <button className="btn btn-primary unity-form-btn-save">
                        SALVAR
                </button>
                </div>
            </div>
        </form>
    );
}
export default UnityForm;
