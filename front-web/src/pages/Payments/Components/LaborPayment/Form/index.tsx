import { makePrivateRequest } from 'core/utils/request';
import { useEffect, useState } from 'react';
import { useForm, Controller } from 'react-hook-form';
import Select from 'react-select';
import { useParams } from 'react-router';
import DateTime from 'react-datetime'
import { toISOFormatDate } from 'core/utils/utils';
import moment from 'moment';
import { toast } from 'react-toastify';
import history from 'core/utils/history';
import { LaborCosType, LaborPayment } from 'core/types/Payment';
import { Employee } from 'core/types/Employee';
import './styles.scss'

type ParamsType = {
    laborPaymentId: string;
}

const LaborPaymentForm = () => {
    const { register, handleSubmit, errors, setValue, control } = useForm<LaborPayment>();
    const [date, setDate] = useState<Date>();
    const { laborPaymentId } = useParams<ParamsType>();
    const isEditing = laborPaymentId !== 'new';
    const [isLoadingEmployees, setIsLoadingEmployees] = useState(false);
    const [employees, setEmployees] = useState<Employee[]>([]);
    const [isLoadingLaborCostTypes, setIsLoadingLaborCostTypes] = useState(false);
    const [laborCostTypes, setLaborCostTypes] = useState<LaborCosType[]>([]);

    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/laborpayments/${laborPaymentId}` })
                .then(response => {
                    setDate(moment(toISOFormatDate(response.data.date)).toDate())
                    setValue('employee', response.data.employee)
                    setValue('laborCostType', response.data.laborCostType)
                    setValue('documentNumber', response.data.documentNumber)
                    setValue('value', response.data.value)
                    setValue('description', response.data.description)
                })
        } else {
            setDate(new Date())
        }
        // eslint-disable-next-line
    }, [laborPaymentId, isEditing, setValue])

    useEffect(() => {
        setIsLoadingEmployees(true)
        makePrivateRequest({ url: `/employees/list?name=` })
            .then((response) => {
                setEmployees(response.data)
            })
            .finally(() => setIsLoadingEmployees(false))
    }, [])

    useEffect(() => {
        setIsLoadingLaborCostTypes(true)
        makePrivateRequest({ url: `/laborcosttypes/list?name=` })
            .then((response) => {
                setLaborCostTypes(response.data)
            })
            .finally(() => setIsLoadingLaborCostTypes(false))
    }, [])

    const onSave = (data: LaborPayment) => {
        const payLoad = {
            ...data,
            date: moment(date).format("DD/MM/YYYY"),
        }
        makePrivateRequest({
            url: isEditing ? `/laborpayments/${laborPaymentId}` : '/laborpayments/',
            method: isEditing ? 'PUT' : 'POST',
            data: payLoad
        })
            .then(() => {
                toast.success("Pagamento salvo salvo com sucesso!")
                history.push('/payments/labor/')
            })
            .catch((error) => {
                toast.error(error.response.data.message)
            })
    }

    const onCancel = () => {
        history.push('/payments/labor/');
    }

    return (
        <div className="laborPaymentForm-main">
            <div className="laborPaymentForm-row">
                <div className="laborPaymentForm-date">
                    <label className="label-base">Dt. Pagamento:</label>
                    <DateTime
                        dateFormat="DD/MM/YYYY"
                        timeFormat={false}
                        onChange={(e) => setDate(moment(e.toString()).toDate())}
                        closeOnSelect={true}
                        locale="pt-br"
                        initialValue={date}
                        value={date}
                    />
                </div>
            </div>

            <div className="laborPaymentForm-row">
                <div className="laborPaymentForm-select-employee">
                    <label className="label-base">Funcionarios:</label>
                    <Controller
                        as={Select}
                        name="employee"
                        rules={{ required: true }}
                        control={control}
                        isLoading={isLoadingEmployees}
                        options={employees}
                        getOptionLabel={(option: Employee) => option.name}
                        getOptionValue={(option: Employee) => String(option.id)}
                        classNamePrefix="employees-select"
                        placeholder="Funcionarios"

                    />
                    {errors.employee && (
                        <div className="invalid-feedback d-block">
                            Campo obrigatório
                        </div>
                    )}
                </div>

                <div className="laborPaymentForm-select-type">
                    <label className="label-base">Tipo:</label>
                    <Controller
                        as={Select}
                        name="laborCostType"
                        rules={{ required: true }}
                        control={control}
                        isLoading={isLoadingLaborCostTypes}
                        options={laborCostTypes}
                        getOptionLabel={(option: LaborCosType) => option.name}
                        getOptionValue={(option: LaborCosType) => String(option.id)}
                        classNamePrefix="laborCostTypes-select"
                        placeholder="Tipo"

                    />
                    {errors.laborCostType && (
                        <div className="invalid-feedback d-block">
                            Campo obrigatório
                        </div>
                    )}
                </div>
            </div>
            <div className="laborPaymentForm-row">
                <div className="laborPaymentForm-doc">
                    <label className="label-base">Documento:</label>
                    <input
                        className="input-base"
                        name="documentNumber"
                        ref={register}
                    />
                </div>
                <div className="laborPaymentForm-value">
                    <label className="label-base">Valor:</label>
                    <input
                        className="input-base"
                        type="number"
                        name="value"
                        ref={register({
                            required: "Campo obrigatório",
                            min: { value: 0.001, message: "O valor dever ser maior que zero" },
                        })}
                    />
                    {errors.value && (
                        <div className="invalid-feedback d-block">
                            {errors.value.message}
                        </div>
                    )}
                </div>
            </div>
            <div className="laborPaymentForm-row">
                <div className="laborPaymentForm-description">
                    <label className="label-base">Descrição:</label>
                    <textarea
                        className="laborPaymentForm-textArea"
                        name="description"
                        ref={register}
                        rows={4}
                    />


                </div>
            </div>

            <div className="laborPaymentForm-actions">
                <div className="laborPaymentForm-actions-cancel">
                    <button
                        className="btn btn-outline-danger"
                        onClick={onCancel}
                    >
                        Cancelar
                    </button>
                </div>
                <div className="laborPaymentForm-actions-save">
                    <button
                        className="btn btn-primary"
                        onClick={handleSubmit(onSave)}
                    >
                        Salvar
                    </button>
                </div>
            </div>

        </div>
    )
}

export default LaborPaymentForm;