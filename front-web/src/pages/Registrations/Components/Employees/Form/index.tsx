import { Sector,Employee } from 'core/types/Employee';
import { makePrivateRequest } from 'core/utils/request';
import { useEffect, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { useHistory, useParams } from 'react-router';
import Select from 'react-select';
import { toast } from 'react-toastify';
import './styles.scss';

type ParamsType = {
    employeeId: string;
}
function EmployeeForm() {
    const { register, handleSubmit, errors, setValue, control } = useForm<Employee>();
    const history = useHistory();
    const { employeeId } = useParams<ParamsType>();
    const isEditing = employeeId !== 'new';
    const [isLoadingSectors, setIsLoadingSectors] = useState(false);
    const [sector, setSector] = useState<Sector>();
        
    useEffect(() => {
        setIsLoadingSectors(true);
        makePrivateRequest({ url: `/sectors/list?name=` })
            .then(response => {
                setSector(response.data)
            })
            .finally(() => {
                setIsLoadingSectors(false);
            })
    }, [])
    
    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/employees/${employeeId}` })
                .then(response => {
                    setValue('name', response.data.name);
                    setValue('sector', response.data.sector);
                })
        }
    }, [employeeId, isEditing, setValue])

    const onSubmit = (data: Employee) => {
        makePrivateRequest({
            url: isEditing ? `/employees/${employeeId}` : '/employees/',
            method: isEditing ? 'PUT' : 'POST',
            data: data
        })
            .then(() => {
                toast.success("Funcionario salvo com sucesso!")
                history.push('/registrations/employees/')
            })
            .catch(() => {
                toast.error("Erro ao salvar funcionario!")
            })
    }

    const handleCancel = ()=>{
        history.push("./")
    }
    return (
        <form onSubmit={handleSubmit(onSubmit)} className="employee-form">
          
            <div className="employee-form-row">
                <div className="employee-form-name">
                    <label className="label-base" >Nome do Funcionario:</label>
                    <input
                        name="name"
                        type="text"
                        className="input-base"
                        ref={register({
                            required: "Campo obrigatório",
                            minLength: { value: 2, message: "O campo deve ter minímo 2 caracteres" },
                            maxLength: { value: 40, message: "O campo deve ter no maximo 10 caracteres" },
                        })}
                    />
                    {errors.name && (
                        <div className="invalid-feedback d-block">
                            {errors.name.message}
                        </div>
                    )}
                </div>
            </div>

            <div className="employee-form-select">
                <label className="label-base">Selecione o setor:</label>
                <Controller
                    as={Select}
                    name="sector"
                    rules={{ required: true }}
                    control={control}
                    isLoading={isLoadingSectors}
                    options={sector}
                    getOptionLabel={(option: Sector) => option.name}
                    getOptionValue={(option: Sector) => String(option.id)}
                    classNamePrefix="sectors-select"
                    placeholder="Setores"
                    defaultValue={null}
                />
                {errors.sector && (
                    <div className="invalid-feedback d-block">
                        Campo obrigatório
                    </div>
                )}
            </div>

            <div className="employee-form-actions">
                <button
                    className="btn btn-outline-danger"
                    onClick={handleCancel}
                >
                    CANCELAR
                </button>
                <button className="btn btn-primary employee-form-btn-save">
                    SALVAR
                </button>
            </div>
        </form>
    );
}
export default EmployeeForm;
