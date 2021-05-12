import { Unity,Group, Product } from 'core/types/Product';
import { makePrivateRequest } from 'core/utils/request';
import { useEffect, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { useHistory, useParams } from 'react-router';
import Select from 'react-select';
import { toast } from 'react-toastify';
import './styles.scss';


type ParamsType = {
    productId: string;
}
function ProductForm() {
    const { register, handleSubmit, errors, setValue, getValues, control } = useForm<Product>();
    const history = useHistory();
    const { productId } = useParams<ParamsType>();
    const isEditing = productId !== 'new';
    const [isLoadingGroups, setIsLoadingGroups] = useState(false);
    const [isLoadingUnits, setIsLoadingUnits] = useState(false);
    const [unity, setUnity] = useState<Unity>();
    const [group, setGroup] = useState<Group>();
    
    useEffect(() => {
        setIsLoadingGroups(true);
        makePrivateRequest({ url: `/groups?listname=` })
            .then(response => {
                setGroup(response.data)
            })
            .finally(() => {
                setIsLoadingGroups(false);
            })
    }, [])
    useEffect(() => {
        setIsLoadingUnits(true);
        makePrivateRequest({ url: `/units?unitylist=` })
            .then(response => {
                setUnity(response.data)
            })
            .finally(() => {
                setIsLoadingUnits(false);
            })
    }, [])



    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/products/${productId}` })
                .then(response => {
                    setValue('name', response.data.name);
                    
                })
        }
    }, [productId, isEditing, setValue])



    const onSubmit = (data: Product) => {
        makePrivateRequest({
            url: isEditing ? `/products/${productId}` : '/products/',
            method: isEditing ? 'PUT' : 'POST',
            data: data
        })
            .then(() => {
                toast.success("Produto salvo com sucesso!")
                history.push('/registrations/products/')
            })
            .catch(() => {
                toast.error("Erro ao salvar produto!")
            })
    }

    const handleCancel = ()=>{
        history.push("./")
    }
    return (
        <form onSubmit={handleSubmit(onSubmit)} className="user-form">
            <div className="user-form-main">
                <div className="user-form-name">
                    <label className="label-base" >Nome Completo:</label>
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
                <div className="user-form-email">
                    <label className="label-base">E-mail:</label>
                    <input
                        type="teste"
                        name="description"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório",
                            minLength: { value: 5, message: "O campo deve ter minímo 5 caracteres" },
                            maxLength: { value: 60, message: "O campo deve ter no maximo 60 caracteres" },
                        })}
                    />
                    {errors.description && (
                        <div className="invalid-feedback d-block">
                            {errors.description?.message}
                        </div>
                    )}
                </div>

            </div>
            <div className="user-form-select">
                <label className="label-base">Selecione o grupo:</label>
                <Controller
                    as={Select}
                    name="group"
                    rules={{ required: true }}
                    control={control}
                    isLoading={isLoadingGroups}
                    options={group}
                    getOptionLabel={(option: Group) => option.name}
                    getOptionValue={(option: Group) => String(option.id)}
                    classNamePrefix="groups-select"
                    placeholder="Grupos"
                />
                {errors.group && (
                    <div className="invalid-feedback d-block">
                        Campo obrigatório
                    </div>
                )}
            </div>

            <div className="user-form-select">
                <label className="label-base">Selecione a unidade:</label>
                <Controller
                    as={Select}
                    name="unity"
                    rules={{ required: true }}
                    control={control}
                    isLoading={isLoadingUnits}
                    options={unity}
                    getOptionLabel={(option: Unity) => option.description}
                    getOptionValue={(option: Unity) => option.id}
                    classNamePrefix="units-select"
                    placeholder="Unidades"
                />
                {errors.unity && (
                    <div className="invalid-feedback d-block">
                        Campo obrigatório
                    </div>
                )}
            </div>

            

            <div className="user-form-actions">
                <button
                    className="btn btn-outline-danger"
                    onClick={handleCancel}
                >
                    CANCELAR
                </button>
                <button className="btn btn-primary user-form-btn-save">
                    SALVAR
                </button>
            </div>
        </form>
    );
}
export default ProductForm;
