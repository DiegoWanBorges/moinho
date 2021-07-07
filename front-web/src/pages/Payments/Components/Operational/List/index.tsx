import { useCallback, useEffect, useState } from 'react';
import DateTime from 'react-datetime'
import moment from 'moment';
import 'moment/locale/pt-br';
import Pagination from 'core/components/Pagination';
import Select from 'react-select';
import { makePrivateRequest } from 'core/utils/request';
import history from 'core/utils/history';
import { toast } from 'react-toastify';
import { OperationalCostType, OperationalPaymentsResponse } from 'core/types/Payment';
import OperationalPaymentCard from '../Card';
import './styles.scss'

const OperationalPaymentList = () => {
    const [startDate, setStartDate] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth(), 1, 0, 0));
    const [endDate, setEndDate] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth() + 1, 0, 23, 59));
    
    const [isLoadingOperationalCostTypes, setIsLoadingOperationalCostTypes] = useState(false);
    const [operationalCostTypes, setOperationalCostTypes] = useState<OperationalCostType[]>([]);
    const [operationalCostType, setOperationalCostType] = useState<OperationalCostType | null>();
   
    const [operationalPaymentsResponse, setOperationalPaymentsResponse] = useState<OperationalPaymentsResponse>();
    const [activePage, setActivePage] = useState(0);

    useEffect(() => {
        setIsLoadingOperationalCostTypes(true)
        makePrivateRequest({ url: `/operationalcosttypes/list?name=` })
            .then((response) => {
                setOperationalCostTypes(response.data)
            })
            .finally(() => setIsLoadingOperationalCostTypes(false))
    }, [])

    

    const getOperationalPayments = useCallback(() => {
        const params = {
            page: activePage,
            startDate: moment(startDate).format("DD/MM/YYYY"),
            endDate: moment(endDate).format("DD/MM/YYYY"),
            linesPerPage: 10,
            orderBy: "date",
            direction: "DESC",
            operationalCostId:operationalCostType?.id
        }

        makePrivateRequest({ url: '/operationalpayments', params })
            .then(response => {
                setOperationalPaymentsResponse(response.data)
                })
            .finally(() => {

            })
    }, [activePage, startDate, endDate, operationalCostType])

    useEffect(() => {
        getOperationalPayments()
        
    }, [activePage, getOperationalPayments])

    const handCreate = () => {
        history.push("/payments/operational/new");
    }

    const onRemove = (OperationalPaymentId: number) => {
        const confirm = window.confirm("Deseja excluir o pagamento selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/operationalpayments/${OperationalPaymentId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Pagamento excluido com sucesso!")
                    history.push('/payments/operational/')
                    getOperationalPayments();
                })
                .catch((error) => {
                    toast.error(error.response.data.message)
                    history.push('/payments/operational/')
                })
        }
    }
    return (
        <div className="OperationalPaymentList-main">
            <div className="OperationalPaymentList-actions">
                <button
                    className="btn btn-primary btn-lg OperationalPaymentList-btnAdd"
                    onClick={handCreate}
                >
                    ADCIONAR
               </button>
            </div>

            <div className="OperationalPaymentList-filter">

                <div className="OperationalPaymentList-filter-startDate">
                    <label className="label-base">Dt. Inicial:</label>
                    <DateTime
                        dateFormat="DD/MM/YYYY"
                        timeFormat={false}
                        onChange={(e) => setStartDate(moment(e.toString()).toDate())}
                        closeOnSelect={true}
                        locale="pt-br"
                        initialValue={startDate}

                    />
                </div>

                <div className="OperationalPaymentList-filter-endDate">
                    <label className="label-base">Dt. Final:</label>
                    <DateTime
                        dateFormat="DD/MM/YYYY"
                        timeFormat={false}
                        onChange={(e) => setEndDate(moment(e.toString()).toDate())}
                        closeOnSelect={true}
                        locale="pt-br"
                        initialValue={endDate}
                    />
                </div>

                
                <div className="OperationalPaymentList-filter-product">
                    <label className="label-base">Tipo:</label>
                    <Select
                        options={operationalCostTypes}
                        isLoading={isLoadingOperationalCostTypes}
                        getOptionLabel={(option: OperationalCostType) => option.name}
                        getOptionValue={(option: OperationalCostType) => String(option.id)}
                        placeholder="Todos"
                        onChange={(e) => setOperationalCostType(e)}
                        isClearable
                    />
                </div>

            </div>
            {operationalPaymentsResponse?.content.map(item => (
                <OperationalPaymentCard 
                    operationalPayment={item}
                    onRemove={onRemove}
                    key={item.id}
                />
            ))

            }
            {operationalPaymentsResponse &&
                <Pagination
                    totalPages={operationalPaymentsResponse?.totalPages}
                    onChange={page => setActivePage(page)}
                />
            }
        </div>
    )
}

export default OperationalPaymentList