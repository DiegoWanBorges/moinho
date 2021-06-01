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
import {OperationalCostType, OperationalPayment } from 'core/types/Payment';

import './styles.scss'

type ParamsType = {
    operationalPaymentId: string;
}

const OperationalPaymentForm = () => {
    const { register, handleSubmit, errors, setValue, control } = useForm<OperationalPayment>();
    const [date, setDate] = useState<Date>();
    const { operationalPaymentId } = useParams<ParamsType>();
    const isEditing = operationalPaymentId !== 'new';
    
    const [isLoadingOperationalCostTypes, setIsLoadingOperationalCostTypes] = useState(false);
    const [operationalCostTypes, setOperationalCostTypes] = useState<OperationalCostType[]>([]);

    useEffect(() => {
        if (isEditing) {
            makePrivateRequest({ url: `/operationalpayments/${operationalPaymentId}` })
                .then(response => {
                    setDate(moment(toISOFormatDate(response.data.date)).toDate())
                    setValue('operationalCostType',response.data.operationalCostType)
                    setValue('documentNumber',response.data.documentNumber)
                    setValue('value',response.data.value)
                    setValue('description',response.data.description)
                })
        } else {
            setDate(new Date())
        }
        // eslint-disable-next-line
    }, [operationalPaymentId, isEditing, setValue])

    useEffect(() => {
        setIsLoadingOperationalCostTypes(true)
        makePrivateRequest({ url: `/operationalcosttypes?listname=` })
            .then((response) => {
                setOperationalCostTypes(response.data)
            })
            .finally(() => setIsLoadingOperationalCostTypes(false))
    }, [])

    const onSave = (data: OperationalPayment) => {
        const payLoad = {
            ...data,
            date: moment(date).format("DD/MM/YYYY"),
        }
        makePrivateRequest({
            url: isEditing ? `/operationalpayments/${operationalPaymentId}` : '/operationalpayments/',
            method: isEditing ? 'PUT' : 'POST',
            data: payLoad
        })
            .then(() => {
                toast.success("Pagamento registrado com sucesso!")
                history.push('/payments/operational/')
            })
            .catch(() => {
                toast.error("Erro ao salvar pagamento!")
            })
    }

    const onCancel = () => {
        history.push('/payments/operational/');
    }

    return (
        <div className="operationalPaymentForm-main">
            <div className="operationalPaymentForm-row">
                <div className="operationalPaymentForm-date">
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

            <div className="operationalPaymentForm-row">
                <div className="operationalPaymentForm-select-type">
                    <label className="label-base">Tipo:</label>
                    <Controller
                        as={Select}
                        name="operationalCostType"
                        rules={{ required: true }}
                        control={control}
                        isLoading={isLoadingOperationalCostTypes}
                        options={operationalCostTypes}
                        getOptionLabel={(option: OperationalCostType) => option.name}
                        getOptionValue={(option: OperationalCostType) => String(option.id)}
                        classNamePrefix="operationalCostTypes-select"
                        placeholder="Tipo"

                    />
                    {errors.operationalCostType && (
                        <div className="invalid-feedback d-block">
                            Campo obrigatório
                        </div>
                    )}
                </div>
            </div>
            <div className="operationalPaymentForm-row">
                <div className="operationalPaymentForm-doc">
                    <label className="label-base">Documento:</label>
                    <input
                        className="input-base"
                        name="documentNumber"
                        ref={register}
                    />
                </div>
                <div className="operationalPaymentForm-value">
                    <label className="label-base">Valor:</label>
                    <input
                        className="input-base"
                        name="value"
                        ref={register}
                        type="number"
                    />
                </div>
            </div>
            <div className="operationalPaymentForm-row">
                <div className="operationalPaymentForm-description">
                    <label className="label-base">Descrição:</label>
                    <textarea 
                        className="input-base"
                        name="description"
                        ref={register}
                    />

                    
                </div>
            </div>
            <div className="operationalPaymentForm-row">
                <div className="operationalPaymentForm-actions">
                    <button
                        className="btn btn-outline-danger"
                        onClick={onCancel}
                    >
                        Cancelar
                    </button>
                    <button
                        className="btn btn-primary operationalPaymentForm-btnSave"
                        onClick={handleSubmit(onSave)}
                    >
                        Salvar
                    </button>


                </div>
            </div>
        </div>
    )
}

export default OperationalPaymentForm;