import Filter from 'core/components/Filter';
import Pagination from 'core/components/Pagination';
import { OperationalCostTypesResponse } from 'core/types/OperationalCostType';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';
import OperationalCostTypesCard from '../Card';

import './styles.scss';

function OperationalCostTypesList() {
    const [operationalCostTypesResponse, setOperationalCostTypesResponse] = useState<OperationalCostTypesResponse>();
    const [activePage, setActivePage] = useState(0);
    const [name, setName] = useState('');
  
    const getOperationalCostTypes = useCallback(() => {
        const params = {
            page: activePage,
            linesPerPage: 3,
            name:name,
            orderBy:"id",
            direction:"DESC"
        }
        makePrivateRequest({ url: '/operationalcosttypes', params })
            .then(response => setOperationalCostTypesResponse(response.data))
            .finally(() => {
    
            })
    }, [activePage,name])

    useEffect(() => {
        getOperationalCostTypes();
    }, [getOperationalCostTypes])

    const history = useHistory();


    const handCreate = () => {
        history.push("/registrations/operationalcosttypes/new");
    }
    const onRemove = (operationalCostTypesId: number) => {
        const confirm = window.confirm("Deseja excluir o tipo de custo operacional selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/operationalcosttypes/${operationalCostTypesId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Tipo de custo operacional excluido com sucesso!")
                    history.push('/registrations/operationalcosttypes')
                    getOperationalCostTypes();
                })
                .catch(() => {
                    toast.error("Falha ao excluir tipo de custo operacional!")
                    history.push('/registrations/operationalcosttypes')
                })
        }
    }
    const handleChangeName = (name: string) => {
        setActivePage(0);
        setName(name);
    }
    const clearFilters = () => {
        setActivePage(0);
        setName('');
    }
    return (
        <div className="operationalCostTypeCard-list">
           <div className="operationalCostTypeCard-list-add-filter">
               <button
                className="btn btn-primary btn-lg operationalCostTypeCard-list-btn-add"
                onClick={handCreate}
               >
                   ADCIONAR
               </button>
               <Filter
                        name={name}
                        handleChangeName={handleChangeName}
                        clearFilters={clearFilters}
                        placeholder="Digite o nome do tipo de custo operacional"
                />
           </div>
           <div className="admin-list-container">
                {operationalCostTypesResponse?.content.map(operationalCostType => (
                    <OperationalCostTypesCard
                        operationalCostType={operationalCostType} key={operationalCostType.id}
                        onRemove={onRemove}
                    />
                ))}

                {operationalCostTypesResponse &&
                    <Pagination
                        totalPages={operationalCostTypesResponse?.totalPages}
                        onChange={page => setActivePage(page)}
                    />
                }
            </div>
        </div>
    );
}
export default OperationalCostTypesList;
