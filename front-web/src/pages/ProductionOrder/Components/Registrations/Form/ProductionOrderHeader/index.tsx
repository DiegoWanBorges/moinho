import { ProductionOrder } from 'core/types/ProductionOrder';
import { toISOFormat } from 'core/utils/utils';
import moment from 'moment';
import { useEffect, useState } from 'react';
import DateTime from 'react-datetime'
import { useForm } from 'react-hook-form';

import './styles.scss';

type Props = {
    productionOrder: ProductionOrder;
}
type FormData = {
    id: number;
    status: string;
}

const ProductionOrderHeader = ({ productionOrder }: Props) => {
    const [startDate, setStartDate] = useState<Date>();
    const [endDate, setEndDate] = useState<Date>();
    const { register, handleSubmit, setValue } = useForm<FormData>();

    useEffect(() =>{
        setStartDate(moment(toISOFormat(productionOrder.startDate)).toDate());
        if(productionOrder.endDate !=null) {
            setEndDate(moment(toISOFormat(productionOrder.endDate)).toDate());
        }
         

    },[productionOrder])

    const onSave = (e:React.MouseEvent<HTMLButtonElement, MouseEvent>) =>{
        e.preventDefault();
    }

    return (
        <form className="production-order-header-main">
            <div>
                <h5>{`O.P.: ${productionOrder.id}`}</h5>
                <h5>
                    {`${productionOrder.formulation.description}
                       - Quantidade Esperada: ${productionOrder.expectedAmount}
                    `}
                </h5>
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
                        onChange={(e) => setEndDate(moment(e.toString()).toDate())}
                        closeOnSelect={true}
                        locale="pt-br"
                        initialValue={endDate}
                        value={endDate}
                    />
                </div>
            </div>
            <div className="production-order-header-text-area">
                <label className="label-base">Observação:</label>
                <textarea>

                </textarea>
            </div>
            <div className="production-order-header-actions">
                <button
                    className="btn btn-secondary"
                >
                    Cancelar
                </button>
                <button
                    className="btn btn-success production-order-header-actions-save"
                    onClick={(e) =>onSave(e)}
                >
                    Salvar
                </button>
            </div>
            <small className="production-order-header-text-inf">*Para finalizar a ordem de proução informe a data final.</small>

        </form>
    )
}

export default ProductionOrderHeader