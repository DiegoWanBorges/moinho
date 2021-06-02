import Filter from 'core/components/Filter';
import Pagination from 'core/components/Pagination';
import { EmployeesResponse } from 'core/types/Employee';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';
import EmployeeCard from '../Card';

import './styles.scss';

function EmployeeList() {
    const [employeesResponse, setEmployeesResponse] = useState<EmployeesResponse>();
    const [activePage, setActivePage] = useState(0);
    const [name, setName] = useState('');
    const [linesPerPage,setLinesPerPage]=useState(10);

    const getEmployees = useCallback(() => {
        const params = {
            page: activePage,
            linesPerPage: linesPerPage,
            name:name,
            orderBy:"id",
            direction:"DESC"
        }
        makePrivateRequest({ url: '/employees', params })
            .then(response => setEmployeesResponse(response.data))
            .finally(() => {
    
            })
    }, [activePage,name,linesPerPage])

    useEffect(() => {
        getEmployees();
    }, [getEmployees])

    const history = useHistory();


    const handCreate = () => {
        history.push("/registrations/employees/new");
    }
    const onRemove = (employeeId: number) => {
        const confirm = window.confirm("Deseja excluir o funcionario selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/employees/${employeeId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Funcionario excluido com sucesso!")
                    history.push('/registrations/employees')
                    getEmployees();
                })
                .catch(() => {
                    toast.error("Falha ao excluir funcionario!")
                    history.push('/registrations/employees')
                })
        }
    }
    const handleChangeName = (name: string) => {
        setActivePage(0);
        setName(name);
    }
    const handleChangeLinesPerPage = (linesPerPage: number) => {
        setActivePage(0);
        setLinesPerPage(linesPerPage)
    }
    const clearFilters = () => {
        setActivePage(0);
        setName('');
        setLinesPerPage(10);
    }
    return (
        <div className="employee-list">
           <div className="employee-list-add-filter">
               <button
                className="btn btn-primary btn-lg employee-list-btn-add"
                onClick={handCreate}
               >
                   ADCIONAR
               </button>
               <Filter
                        name={name}
                        handleChangeName={handleChangeName}
                        linesPerPage={linesPerPage}
                        handleChangeLinesPerPage={handleChangeLinesPerPage}
                        clearFilters={clearFilters}
                        placeholder="Digite o nome do funcionario"
                />
           </div>
           <div className="admin-list-container">
                {employeesResponse?.content.map(employee => (
                    <EmployeeCard
                        employee={employee} key={employee.id}
                        onRemove={onRemove}
                    />
                ))}

                {employeesResponse &&
                    <Pagination
                        totalPages={employeesResponse?.totalPages}
                        onChange={page => setActivePage(page)}
                    />
                }
            </div>
        </div>
    );
}
export default EmployeeList;
