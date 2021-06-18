import { useCallback, useEffect, useState } from 'react';
import './styles.scss'
import DateTime from 'react-datetime'
import moment from 'moment';
import 'moment/locale/pt-br';
import Pagination from 'core/components/Pagination';
import Select from 'react-select';
import { makePrivateRequest } from 'core/utils/request';
import history from 'core/utils/history';
import { toast } from 'react-toastify';
import { Employee } from 'core/types/Employee';
import { LaborCosType, LaborPaymentsResponse } from 'core/types/Payment';
import LaborPaymentCard from '../Card';

const LaborPaymentList = () => {
    const [startDate, setStartDate] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth(), 1, 0, 0));
    const [endDate, setEndDate] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth() + 1, 0, 23, 59));
    
    const [isLoadingEmployees, setIsLoadingEmployees] = useState(false);
    const [employees, setEmployees] = useState<Employee[]>([]);
    const [employee, setEmployee] = useState<Employee | null>();
   
    const [isLoadingLaborCostTypes, setIsLoadingLaborCostTypes] = useState(false);
    const [laborCostTypes, setLaborCostTypes] = useState<LaborCosType[]>([]);
    const [laborCostType, setLaborCostType] = useState<LaborCosType | null>();
   
    const [laborPaymentsResponse, setLaborPaymentsResponse] = useState<LaborPaymentsResponse>();
    const [activePage, setActivePage] = useState(0);

    useEffect(() => {
        setIsLoadingEmployees(true)
        makePrivateRequest({ url: `/employees?listname=` })
            .then((response) => {
                setEmployees(response.data)
            })
            .finally(() => setIsLoadingEmployees(false))
    }, [])

    useEffect(() => {
        setIsLoadingLaborCostTypes(true)
        makePrivateRequest({ url: `/laborcosttypes?listname=` })
            .then((response) => {
                setLaborCostTypes(response.data)
            })
            .finally(() => setIsLoadingLaborCostTypes(false))
    }, [])

    const getLaborPayments = useCallback(() => {
        const params = {
            page: activePage,
            startDate: moment(startDate).format("DD/MM/YYYY"),
            endDate: moment(endDate).format("DD/MM/YYYY"),
            linesPerPage: 10,
            orderBy: "date",
            direction: "DESC",
            employeeId: employee?.id,
            laborCostTypeId:laborCostType?.id,
        }

        makePrivateRequest({ url: '/laborpayments', params })
            .then(response => {
                setLaborPaymentsResponse(response.data)
                })
            .finally(() => {

            })
    }, [activePage, startDate, endDate, employee,laborCostType])

    useEffect(() => {
        getLaborPayments()
        
    }, [activePage, getLaborPayments])

    const handCreate = () => {
        history.push("/payments/labor/new");
    }

    const onRemove = (stockMovementId: number) => {
        const confirm = window.confirm("Deseja excluir o movimento selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/laborpayments/${stockMovementId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Movimentação de estoque excluida com sucesso!")
                    history.push('/payments/labor/')
                    getLaborPayments();
                })
                .catch((error) => {
                    toast.error(error.response.data.message)
                    history.push('/payments/labor/')
                })
        }
    }
    return (
        <div className="stockMovementList-main">
            <div className="stockMovementList-actions">
                <button
                    className="btn btn-primary btn-lg stockMovementList-btnAdd"
                    onClick={handCreate}
                >
                    ADCIONAR
               </button>
            </div>

            <div className="stockMovementList-filter">

                <div className="stockMovementList-filter-startDate">
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

                <div className="stockMovementList-filter-endDate">
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

                <div className="stockMovementList-filter-product">
                    <label className="label-base">Funcionario:</label>
                    <Select
                        options={employees}
                        isLoading={isLoadingEmployees}
                        getOptionLabel={(option: Employee) => option.name}
                        getOptionValue={(option: Employee) => String(option.id)}
                        placeholder="Todos"
                        onChange={(e) => setEmployee(e)}
                        isClearable
                    />
                </div>
                <div className="stockMovementList-filter-product">
                    <label className="label-base">Tipo:</label>
                    <Select
                        options={laborCostTypes}
                        isLoading={isLoadingLaborCostTypes}
                        getOptionLabel={(option: LaborCosType) => option.name}
                        getOptionValue={(option: LaborCosType) => String(option.id)}
                        placeholder="Todos"
                        onChange={(e) => setLaborCostType(e)}
                        isClearable
                    />
                </div>

            </div>
            {laborPaymentsResponse?.content.map(item => (
                <LaborPaymentCard 
                    laborPayment={item}
                    onRemove={onRemove}
                    key={item.id}
                />
            ))

            }
            {laborPaymentsResponse &&
                <Pagination
                    totalPages={laborPaymentsResponse?.totalPages}
                    onChange={page => setActivePage(page)}
                />
            }
        </div>
    )
}

export default LaborPaymentList