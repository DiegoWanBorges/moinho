import { ProductionOrder } from 'core/types/ProductionOrder';
import history from 'core/utils/history';
import { makePrivateRequest } from 'core/utils/request';
import { useEffect, useState } from 'react';
import DateTime from 'react-datetime'
import { toISOFormatDateTime } from 'core/utils/utils';
import moment from 'moment';
import { toast } from 'react-toastify';


import './styles.scss';

type Props = {
    productionOrder: ProductionOrder;
}
const ProductionOrderHeader = ({ productionOrder }: Props) => {
    const [startDate, setStartDate] = useState<Date>();
    const [endDate, setEndDate] = useState<Date|undefined>();
    const [observation, setObservation] = useState(' ');

    useEffect(() => {
        setStartDate(moment(toISOFormatDateTime(productionOrder.startDate)).toDate());
        if (productionOrder.endDate != null) {
            setEndDate(moment(toISOFormatDateTime(productionOrder.endDate)).toDate());
        }
        setObservation(productionOrder.observation)

    }, [productionOrder])

    const onSave = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault();
        console.log(endDate)
        const data = {
            startDate: moment(startDate).format("DD/MM/YYYY HH:mm"),
            endDate: endDate === undefined ? null : moment(endDate).format("DD/MM/YYYY HH:mm"),
            observation: observation
        }
        makePrivateRequest({
            url: `/productionorders/${productionOrder.id}`,
            method: 'PUT',
            data: data
        })
            .then(() => {
                toast.success("Ordem de produção atualizada com sucesso!")
                history.push('/productions/registrations/')
            })
            .catch(() => {
                toast.error("Erro ao salvar ordem de produção !")
            })

    }

    const onCancel = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault();
        history.push('./')
    }

    return (
        <form className="production-order-header-main">
            <div>
                <h6>{`O.P.: ${productionOrder.id}`}</h6>
                <h6>{`${productionOrder.formulation.description}`}</h6>
                <h6>{`Quantidade Esperada: ${productionOrder.expectedAmount}`}</h6>
                <small>{`Dt. Emissão: ${productionOrder.emission}`}</small>
            </div>

            <div className="production-order-header-date">

                <div className="production-order-header-date-start">
                    <label className="label-base">Dt. Inicial:</label>
                    <DateTime
                        dateFormat="DD/MM/YYYY"
                        timeFormat="HH:mm"
                        onChange={(e) => setStartDate(moment(e.toString()).toDate())}
                        closeOnSelect={true}
                        locale="pt-br"
                        initialValue={startDate}
                        value={startDate}
                    />
                </div>
                <div className="production-order-header-date-end">
                    <label className="label-base">Dt. Final:</label>
                    <DateTime
                        dateFormat="DD/MM/YYYY"
                        timeFormat="HH:mm"
                        onChange={(e) => e.toString() === '' ? setEndDate(undefined) : setEndDate(moment(e.toString()).toDate())}
                        closeOnSelect={true}
                        locale="pt-br"
                        initialValue={endDate}
                        value={endDate}                        
                    />
                </div>
            </div>
            <div className="production-order-header-text-area">
                <label className="label-base">Observação:</label>
                <textarea
                    onChange={(e) => setObservation(e.target.value)}
                    value={observation}

                />


            </div>
            <div className="production-order-header-actions">
                <button
                    className="btn btn-secondary"
                    onClick={(e) => onCancel(e)}
                >
                    Cancelar
                </button>
                <button
                    className="btn btn-success production-order-header-actions-save"
                    onClick={(e) => onSave(e)}
                >
                    Salvar
                </button>
            </div>
            <small className="production-order-header-text-inf">*Para finalizar a ordem de produção informe a data final.</small>

        </form>
    )
}

export default ProductionOrderHeader