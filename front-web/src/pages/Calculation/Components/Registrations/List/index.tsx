import DateTime from 'react-datetime'
import moment from 'moment';
import 'moment/locale/pt-br';
import './styles.scss';
import { useCallback, useState } from 'react';
import { useEffect } from 'react';
import { CostCalculationsResponse } from 'core/types/CostCalculation';
import { makePrivateRequest } from 'core/utils/request';
import Pagination from 'core/components/Pagination';
import { useHistory } from 'react-router-dom';
import { toast } from 'react-toastify';
import CostCalculationCard from '../Card';

function CalculationList() {
    const [startReferenceMonth, setStartReferenceMonth] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth(), 1));
    const [endReferenceMonth, setEndReferenceMonth] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth(), 1));

    const [costCalculationsResponse, setCostCalculationsResponse] = useState<CostCalculationsResponse>();
    const [activePage, setActivePage] = useState(0);

    const getCostCalculations = useCallback(() => {
        const params = {
            page: activePage,
            startDate: moment(startReferenceMonth).format("DD/MM/YYYY"),
            endDate: moment(endReferenceMonth).format("DD/MM/YYYY"),
            linesPerPage: 10,
        }
        makePrivateRequest({ url: '/costcalculations', params })
            .then(response => setCostCalculationsResponse(response.data))
            .finally(() => {

            })
    }, [activePage, startReferenceMonth, endReferenceMonth])

    useEffect(() => {
        getCostCalculations();
    }, [getCostCalculations])

    const history = useHistory();


    const handCreate = () => {
        history.push("/calculations/registrations/create");
    }
    const onRemove = (costCalculationId: number) => {
        const confirm = window.confirm("Deseja excluir a apuração selecionada?");
        if (confirm) {
            makePrivateRequest({
                url: `/costcalculations/${costCalculationId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Apuração excluida com sucesso!")
                    history.push('/calculations/registrations')
                    getCostCalculations();
                })
                .catch(() => {
                    toast.error("Falha ao excluir apuração!")
                    history.push('/calculations/registrations')
                })
        }
    }
    

    return (
        <div className="calculation-list">
            <div className="calculation-list-actions">
                <button
                    className="btn btn-primary btn-lg"
                    onClick={handCreate}
                >
                    Nova Apuração
                </button>
            </div>

            <div className="calculation-list-filter">
                <div className="calculation-list-filter-initial-date">
                    <label className="label-base">Mês Referencia Inicial:</label>
                    <DateTime
                        dateFormat="MM/YYYY"
                        timeFormat={false}
                        closeOnSelect={true}
                        onChange={(e) => setStartReferenceMonth(moment(e.toString()).toDate())}
                        locale="pt-br"
                        initialValue={startReferenceMonth}
                    />
                </div>
                <div className="calculation-list-filter-end-date">
                    <label className="label-base">Mês Referencia Final:</label>
                    <DateTime
                        dateFormat="MM/YYYY"
                        timeFormat={false}
                        closeOnSelect={true}
                        onChange={(e) => setEndReferenceMonth(moment(e.toString()).toDate())}
                        locale="pt-br"
                        initialValue={endReferenceMonth}
                    />
                </div>
            </div>
            {costCalculationsResponse?.content.map(item => (
                <CostCalculationCard
                    costCalculation={item} key={item.id}
                    onRemove={onRemove}
                />
            ))}
            {costCalculationsResponse &&
                <Pagination
                    totalPages={costCalculationsResponse?.totalPages}
                    onChange={page => setActivePage(page)}
                />
            }


        </div>
    );
}
export default CalculationList;
