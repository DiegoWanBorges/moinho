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
    sectorId: string;
}
function SectorForm() {
    const { register, handleSubmit, errors, setValue } = useForm<FormState>();
    const history = useHistory();
    const { sectorId } = useParams<ParamsType>();
    const isEditing = sectorId !== 'new';


    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/sectors/${sectorId}` })
                .then(response => {
                    setValue('name', response.data.name);
                })
        }
    }, [sectorId, isEditing, setValue])



    const onSubmit = (data: FormState) => {
        makePrivateRequest({
            url: isEditing ? `/sectors/${sectorId}` : '/sectors/',
            method: isEditing ? 'PUT' : 'POST',
            data: data
        })
            .then(() => {
                toast.success("Setor salvo com sucesso!")
                history.push('/registrations/sectors/')
            })
            .catch(() => {
                toast.error("Erro ao salvar setor!")
            })
    }

    const handleCancel = () => {
        history.push("./") 
    }
    return (
        <form onSubmit={handleSubmit(onSubmit)} className="sector-form">
            <div className="sector-form-main">

                <div className="sector-form-name">
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

                <div className="sector-form-actions">
                    <button
                        className="btn btn-outline-danger"
                        onClick={handleCancel}
                    >
                        CANCELAR
                </button>
                    <button className="btn btn-primary sector-form-btn-save">
                        SALVAR
                </button>
                </div>
            </div>
        </form>
    );
}
export default SectorForm;
