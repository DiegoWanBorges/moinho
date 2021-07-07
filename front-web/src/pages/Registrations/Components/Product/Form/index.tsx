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
    const { register, handleSubmit, errors, setValue, control } = useForm<Product>();
    const history = useHistory();
    const { productId } = useParams<ParamsType>();
    const isEditing = productId !== 'new';
    const [isLoadingGroups, setIsLoadingGroups] = useState(false);
    const [isLoadingUnits, setIsLoadingUnits] = useState(false);
    const [unity, setUnity] = useState<Unity>();
    const [group, setGroup] = useState<Group>();
    
    useEffect(() => {
        setIsLoadingGroups(true);
        makePrivateRequest({ url: `/groups/list?name=` })
            .then(response => {
                setGroup(response.data)
            })
            .finally(() => {
                setIsLoadingGroups(false);
            })
    }, [])
    useEffect(() => {
        setIsLoadingUnits(true);
        makePrivateRequest({ url: `/units/list?description=` })
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
                    setValue('description', response.data.description);
                    setValue('packaging', response.data.packaging);
                    setValue('netWeight', response.data.netWeight);
                    setValue('grossWeight', response.data.grossWeight);
                    setValue('validityDays', response.data.validityDays);
                    setValue('rawMaterialConversion', response.data.rawMaterialConversion);
                    setValue('costLastEntry', response.data.costLastEntry);
                    setValue('averageCost', response.data.averageCost);
                    setValue('stockBalance', response.data.stockBalance);
                    setValue('unity', response.data.unity);
                    setValue('group', response.data.group);

                    
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
        <form onSubmit={handleSubmit(onSubmit)} className="product-form">
          
            <div className="product-form-row">
                <div className="product-form-name">
                    <label className="label-base" >Nome do Produto:</label>
                    <input
                        name="name"
                        type="text"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório",
                            minLength: { value: 2, message: "O campo deve ter minímo 2 caracteres" },
                            maxLength: { value: 100, message: "O campo deve ter no maximo 100 caracteres" },
                        })}
                    />
                    {errors.name && (
                        <div className="invalid-feedback d-block">
                            {errors.name.message}
                        </div>
                    )}
                </div>

                <div className="product-form-description">
                    <label className="label-base">Descrição:</label>
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

            <div className="product-form-row">
                <div className="product-form-row-item">
                    <label className="label-base" >Embalagem:</label>
                    <input
                        name="packaging"
                        type="text"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório",
                            minLength: { value: 2, message: "O campo deve ter minímo 2 caracteres" },
                            maxLength: { value: 40, message: "O campo deve ter no maximo 10 caracteres" },
                        })}
                    />
                    {errors.packaging && (
                        <div className="invalid-feedback d-block">
                            {errors.packaging.message}
                        </div>
                    )}
                </div>
                <div className="product-form-row-item">
                    <label className="label-base">Peso Liquido:</label>
                    <input
                        type="number"
                        step="0.001"
                        name="netWeight"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório"
                        })}
                    />
                    {errors.netWeight && (
                        <div className="invalid-feedback d-block">
                            {errors.netWeight?.message}
                        </div>
                    )}
                </div>
                <div className="product-form-row-item">
                    <label className="label-base">Peso Bruto:</label>
                    <input
                        type="number"
                        step="0.001"
                        name="grossWeight"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório"
                        })}
                    />
                    {errors.grossWeight && (
                        <div className="invalid-feedback d-block">
                            {errors.grossWeight?.message}
                        </div>
                    )}
                </div>
                <div className="product-form-row-item">
                    <label className="label-base">Validade(Dias):</label>
                    <input
                        type="number"
                        name="validityDays"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório"
                        })}
                    />
                    {errors.validityDays && (
                        <div className="invalid-feedback d-block">
                            {errors.validityDays?.message}
                        </div>
                    )}
                </div>
            </div>
        
            <div className="product-form-row">
               
                <div className="product-form-row-item">
                    <label className="label-base" >Conver.materia prima:</label>
                    <input
                        name="rawMaterialConversion"
                        type="text"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório"
                        })}
                    />
                    {errors.rawMaterialConversion && (
                        <div className="invalid-feedback d-block">
                            {errors.rawMaterialConversion.message}
                        </div>
                    )}
                </div>

                <div className="product-form-row-item">
                    <label className="label-base">Custo Ult. Ent.:</label>
                    <input
                        type="number"
                        name="costLastEntry"
                        className="input-base"
                        ref={register}
                        readOnly
                    />
                </div>

                <div className="product-form-row-item">
                    <label className="label-base">Custo Médio:</label>
                    <input
                        type="number"
                        name="averageCost"
                        className="input-base"
                        ref={register}
                        readOnly
                    />
                </div>

                <div className="product-form-row-item">
                    <label className="label-base">Saldo Estoque:</label>
                    <input
                        type="number"
                        name="stockBalance"
                        className="input-base"
                        ref={register}
                        readOnly
                    />
                </div>
            </div>

            <div className="product-form-select">
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
                    defaultValue={null}
                />
                {errors.group && (
                    <div className="invalid-feedback d-block">
                        Campo obrigatório
                    </div>
                )}
            </div>

            <div className="product-form-select">
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
                    defaultValue={null}
                />
                {errors.unity && (
                    <div className="invalid-feedback d-block">
                        Campo obrigatório
                    </div>
                )}
            </div>

            <div className="product-form-actions">
                <button
                    className="btn btn-outline-danger"
                    onClick={handleCancel}
                >
                    CANCELAR
                </button>
                <button className="btn btn-primary product-form-btn-save">
                    SALVAR
                </button>
            </div>
        </form>
    );
}
export default ProductForm;
