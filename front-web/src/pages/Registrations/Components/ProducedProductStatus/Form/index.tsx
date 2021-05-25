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
    producedProductStatusId: string;
}
function ProducedProductStatusForm() {
    const { register, handleSubmit, errors, setValue } = useForm<FormState>();
    const history = useHistory();
    const { producedProductStatusId } = useParams<ParamsType>();
    const isEditing = producedProductStatusId !== 'new';


    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/producedproductstatus/${producedProductStatusId}` })
                .then(response => {
                    setValue('name', response.data.name);
                })
        }
    }, [producedProductStatusId, isEditing, setValue])



    const onSubmit = (data: FormState) => {
        makePrivateRequest({
            url: isEditing ? `/producedproductstatus/${producedProductStatusId}` : '/producedproductstatus/',
            method: isEditing ? 'PUT' : 'POST',
            data: data
        })
            .then(() => {
                toast.success("Status do Pallet salvo com sucesso!")
                history.push('/registrations/producedproductstatus/')
            })
            .catch(() => {
                toast.error("Erro ao salvar Status do Pallet!")
            })
    }

    const handleCancel = () => {
        history.push("./") 
    }
    return (
        <form onSubmit={handleSubmit(onSubmit)} className="producedProductStatus-form">
            <div className="producedProductStatus-form-main">

                <div className="producedProductStatus-form-name">
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

                <div className="producedProductStatus-form-actions">
                    <button
                        className="btn btn-outline-danger"
                        onClick={handleCancel}
                    >
                        CANCELAR
                </button>
                    <button className="btn btn-primary producedProductStatus-form-btn-save">
                        SALVAR
                </button>
                </div>
            </div>
        </form>
    );
}
export default ProducedProductStatusForm;
