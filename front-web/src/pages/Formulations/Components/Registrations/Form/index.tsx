import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { useForm, Controller } from 'react-hook-form';
import { useHistory, useParams } from 'react-router';
import { toast } from 'react-toastify';
import './styles.scss';
import Select from 'react-select';
import { Product } from 'core/types/Product';
import FormulationItems from './FormulationItems';
import { Sector } from 'core/types/Employee';
import { OperationalCostType } from 'core/types/Payment';
import { Formulation, FormulationItem } from 'core/types/Formulation';
import FormulationItemsCard from './FormulationItemsCard';

type ParamsType = {
    formulationId: string;
}
function FormulationForm() {
    const { register, handleSubmit, errors, setValue, control } = useForm<Formulation>();
    const history = useHistory();
    const { formulationId } = useParams<ParamsType>();
    const isEditing = formulationId !== 'new';
    const [isLoadingProducts, setIsLoadingProducts] = useState(false);
    const [products, setProducts] = useState<Product[]>();
    const [isLoadingSectors, setIsLoadingSectors] = useState(false);
    const [sectors, setSectors] = useState<Sector[]>();
    const [isLoadingOperationalCostTypes, setIsLoadingOperationalCostTypes] = useState(false);
    const [operationalCostTypes, setOperationalCostTypes] = useState<OperationalCostType[]>();
    const [formulationItem, setFormulationItem] = useState<FormulationItem[]>([]);
    const [isLoadingSecondaryProductions, setIsLoadingSecondaryProductions] = useState(false);
    const [secondaryProductions, setSecondaryProductions] = useState<Product[]>();
    useEffect(() => {
        setIsLoadingProducts(true)
        setIsLoadingSecondaryProductions(true)
        makePrivateRequest({ url: `/products?listname=` })
            .then(response => {
                setProducts(response.data)
                setSecondaryProductions(response.data)
            })
            .finally(() => {
                setIsLoadingProducts(false)
                setIsLoadingSecondaryProductions(false)
            })
    }, [])

    useEffect(() => {
        setIsLoadingSectors(true)
        makePrivateRequest({ url: `/sectors?listname=` })
            .then(response => {
                setSectors(response.data)
            })
            .finally(() => setIsLoadingSectors(false))
    }, [])

    useEffect(() => {
        setIsLoadingOperationalCostTypes(true)
        makePrivateRequest({ url: `/operationalcosttypes?listname=` })
            .then(response => {
                setOperationalCostTypes(response.data)
            })
            .finally(() => setIsLoadingOperationalCostTypes(false))
    }, [])

    const getFormulationItems = useCallback(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/formulations/${formulationId}` })
                .then(response => {
                    setValue('description', response.data.description);
                    setValue('coefficient', response.data.coefficient);
                    setValue('product', response.data.product);
                    setValue('sectors', response.data.sectors);
                    setValue('operationalCostType', response.data.operationalCostType);
                    setValue('secondaryProduction', response.data.secondaryProduction);
                    setFormulationItem(response.data.formulationItems)
                })
        }
    }, [formulationId, isEditing, setValue])

    useEffect(() => {
        getFormulationItems();
    }, [getFormulationItems])






    const onSubmit = (data: Formulation) => {
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

    const onInsertItem = (data: FormulationItem) => {
        const payload = {
            ...data,
            formulationId: formulationId
        }
        makePrivateRequest({
            url: '/formulationsitems/',
            method: 'POST',
            data: payload
        })
            .then(() => {
                getFormulationItems();
                toast.success("Item de formulação salvo com sucesso!");
            })
            .catch(() => {
                toast.error("Erro ao salvar item de formulação!")
            })
    }
    const onDeleteItem = (data: FormulationItem) => {
        const confirm = window.confirm("Deseja excluir o ingrediente selecionado da formulação?");
        if (confirm) {
            makePrivateRequest({
                url: `/formulationsitems/${formulationId},${data.product.id}`,
                method: 'DELETE',

            })
                .then(() => {
                    getFormulationItems();
                    toast.success("Item de formulação excluido com sucesso!");
                })
                .catch(() => {
                    toast.error("Erro ao excluir item de formulação!")
                })
        }
    }
    const onEditItem = (data: FormulationItem) => {
        console.log(data)
        makePrivateRequest({
            url: `/formulationsitems/${formulationId},${data.product.id}`,
            method: 'PUT',
            data: data,

        })
            .then(() => {
                getFormulationItems();
                toast.success("Item de formulação atualizado com sucesso!");
            })
            .catch(() => {
                toast.error("Erro ao atualizar item de formulação!")
            })
    }
    const handleCancel = () => {
        history.push("./")
    }
    return (
        <form onSubmit={handleSubmit(onSubmit)} className="formulation-form">

            <div className="formulation-form-row">
                <div className="formulation-form-row-description">
                    <label className="label-base">Descrição:</label>
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

                <div className="formulation-form-row-coefficient">
                    <label className="label-base">Coeficiente:</label>
                    <input
                        name="coefficient"
                        type="number"
                        className="input-base"
                        ref={register}
                    />
                    {errors.coefficient && (
                        <div className="invalid-feedback d-block">
                            {errors.coefficient.message}
                        </div>
                    )}
                </div>
            </div>

            <div className="formulation-form-row">
                <div className="formulation-select-product">
                    <label className="label-base">Produto principal:</label>
                    <Controller
                        as={Select}
                        name="product"
                        rules={{ required: true }}
                        control={control}
                        isLoading={isLoadingProducts}
                        options={products}
                        getOptionLabel={(option: Product) => option.name}
                        getOptionValue={(option: Product) => String(option.id)}
                        classNamePrefix="products-select"
                        placeholder="Produtos"
                    />
                    {errors.product && (
                        <div className="invalid-feedback d-block">
                            Campo obrigatório
                        </div>
                    )}
                </div>
                <div className="formulation-select-product-secondary">
                    <label className="label-base">Produto Secundario:</label>
                    <Controller
                        as={Select}
                        name="secondaryProduction"
                        control={control}
                        isLoading={isLoadingSecondaryProductions}
                        options={secondaryProductions}
                        getOptionLabel={(option: Product) => option.name}
                        getOptionValue={(option: Product) => String(option.id)}
                        classNamePrefix="sectors-select"
                        placeholder="Secundario"
                        isMulti
                    />
                </div>
            </div>

            <div className="formulation-form-row">
                <div className="formulation-select-sector">
                    <label className="label-base">Setores:</label>
                    <Controller
                        as={Select}
                        name="sectors"
                        rules={{ required: true }}
                        control={control}
                        isLoading={isLoadingSectors}
                        options={sectors}
                        getOptionLabel={(option: Sector) => option.name}
                        getOptionValue={(option: Sector) => String(option.id)}
                        classNamePrefix="sectors-select"
                        placeholder="Setores"
                        isMulti
                    />
                    {errors.sectors && (
                        <div className="invalid-feedback d-block">
                            Campo obrigatório
                        </div>
                    )}
                </div>

                <div className="formulation-select-operational">
                    <label className="label-base">Custos Operacionais:</label>
                    <Controller
                        as={Select}
                        name="operationalCostType"
                        rules={{ required: true }}
                        control={control}
                        isLoading={isLoadingOperationalCostTypes}
                        options={operationalCostTypes}
                        getOptionLabel={(option: OperationalCostType) => option.name}
                        getOptionValue={(option: OperationalCostType) => String(option.id)}
                        classNamePrefix="apportionments-select"
                        placeholder="Custos operacionais"
                        isMulti
                    />
                    {errors.operationalCostType && (
                        <div className="invalid-feedback d-block">
                            Campo obrigatório
                        </div>
                    )}
                </div>

            </div>

            <div className="formulation-form-actions">
                <button
                    className="btn btn-outline-danger"
                    onClick={handleCancel}
                >
                    CANCELAR
                </button>
                <button className="btn btn-primary formulation-form-btn-save">
                    SALVAR
                </button>
            </div>

            {
                isEditing && (<FormulationItems
                    onInsertItem={onInsertItem}
                    formulationItem={formulationItem}

                />)
            }
            {
                isEditing && (
                    formulationItem?.map(item => (
                        <FormulationItemsCard
                            formulationItems={item} key={item.product.id}
                            onDeleteItem={onDeleteItem}
                            onEditItem={onEditItem}
                        />
                    )))}
        </form>
    );
}
export default FormulationForm;
