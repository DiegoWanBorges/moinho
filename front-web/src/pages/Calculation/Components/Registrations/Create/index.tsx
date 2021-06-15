import './styles.scss';
import DateTime from 'react-datetime'
import moment from 'moment';
import { useState } from 'react';
import { makePrivateRequest } from 'core/utils/request';
import { toast } from 'react-toastify';
import history from 'core/utils/history';



const CostCalculationCreate = () => {
    const [startDate, setStartDate] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth(), 1, 0, 0));
    const [endDate, setEndDate] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth() + 1, 0, 23, 59));
    const [refMonth, setRefMonth] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth(), 1));
    const [stockDate, setStockDate] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth() + 0, 0));
    
    const onSave =() =>{
        const data ={
            startDate: moment(startDate).format("DD/MM/YYYY HH:mm"),
            endDate: moment(endDate).format("DD/MM/YYYY HH:mm"),
            stockStartDate:moment(stockDate).format("DD/MM/YYYY"),
            referenceMonth:moment(refMonth).format("DD/MM/YYYY"),
        }
        makePrivateRequest({
            url: `/costcalculations`,
            method: 'POST',
            data
        })
            .then(response => {
                toast.success("Apuração cadastrada com sucesso!")
                history.push('/calculations/registrations/')
            })
            .catch((error) => {
                toast.error(error.response.data.message)
            })
        console.log(data)
    }
    
    return (
        <div className="costCalculation-main">
            <div className="costCalculation-ref">
                <label className="label-base">Ref. Mês:</label>
                <DateTime
                    dateFormat="MM/YYYY"
                    timeFormat={false}
                    onChange={(e) => setRefMonth(moment(e.toString()).toDate())}
                    closeOnSelect={true}
                    locale="pt-br"
                    initialValue={refMonth}
                />
            </div>

            <div className="costCalculation-title">
                <label className="label-base">Peiodo de Apuração</label>
            </div>
            <div className="costCalculation-period">
                <div className="costCalculation-period-start">
                    <DateTime
                        dateFormat="DD/MM/YYYY"
                        timeFormat="HH:mm"
                        onChange={(e) => setStartDate(moment(e.toString()).toDate())}
                        closeOnSelect={true}
                        locale="pt-br"
                        initialValue={startDate}
                    />

                </div>
                <div className="costCalculation-period-end">
                    <DateTime
                        dateFormat="DD/MM/YYYY"
                        timeFormat="HH:mm"
                        onChange={(e) => setEndDate(moment(e.toString()).toDate())}
                        closeOnSelect={true}
                        locale="pt-br"
                        initialValue={endDate}
                    />

                </div>
            </div>
            <div className="costCalculation-stock">
                <label className="label-base">Estoque Inicial:</label>
                <DateTime
                    dateFormat="DD/MM/YYYY"
                    timeFormat={false}
                    onChange={(e) => setStockDate(moment(e.toString()).toDate())}
                    closeOnSelect={true}
                    locale="pt-br"
                    initialValue={stockDate}
                />

            </div>
            <div className="costCalculation-actions">
                <button
                    className="btn btn-success"
                    onClick={onSave}
                >
                    Gerar Apuração
                </button>
            </div>
        </div>
    )
}

export default CostCalculationCreate