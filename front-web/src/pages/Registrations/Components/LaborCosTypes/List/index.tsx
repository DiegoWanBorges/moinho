import Filter from 'core/components/Filter';
import Pagination from 'core/components/Pagination';
import { LaborCosTypesResponse } from 'core/types/LaborCosType';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';
import LaborCostTypeCard from '../Card';
import './styles.scss';

function LaborCostTypeList() {
    const [laborCostTypesResponse, setLaborCostTypesResponse] = useState<LaborCosTypesResponse>();
    const [activePage, setActivePage] = useState(0);
    const [name, setName] = useState('');
  
    const getLaborCostTypes = useCallback(() => {
        const params = {
            page: activePage,
            linesPerPage: 3,
            name:name,
            orderBy:"id",
            direction:"DESC"
        }
        makePrivateRequest({ url: '/laborcosttypes', params })
            .then(response => setLaborCostTypesResponse(response.data))
            .finally(() => {
    
            })
    }, [activePage,name])

    useEffect(() => {
        getLaborCostTypes();
    }, [getLaborCostTypes])

    const history = useHistory();


    const handCreate = () => {
        history.push("/registrations/laborcosttypes/new");
    }
    const onRemove = (laborCostType: number) => {
        const confirm = window.confirm("Deseja excluir o tipo de pagamento de funcionario selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/laborcosttypes/${laborCostType}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Tipo de pagamento de funcionario excluido com sucesso!")
                    history.push('/registrations/laborcosttypes')
                    getLaborCostTypes();
                })
                .catch(() => {
                    toast.error("Falha ao excluir tipo de pagamento de funcionario!")
                    history.push('/registrations/laborcosttypes')
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
        <div className="laborCosType-list">
           <div className="laborCosType-list-add-filter">
               <button
                className="btn btn-primary btn-lg laborCosType-list-btn-add"
                onClick={handCreate}
               >
                   ADCIONAR
               </button>
               <Filter
                        name={name}
                        handleChangeName={handleChangeName}
                        clearFilters={clearFilters}
                        placeholder="Digite o nome do tipo de pagamento"
                />
           </div>
           <div className="admin-list-container">
                {laborCostTypesResponse?.content.map(laborCosTypeCard => (
                    <LaborCostTypeCard
                        laborCosType={laborCosTypeCard} key={laborCosTypeCard.id}
                        onRemove={onRemove}
                    />
                ))}

                {laborCostTypesResponse &&
                    <Pagination
                        totalPages={laborCostTypesResponse?.totalPages}
                        onChange={page => setActivePage(page)}
                    />
                }
            </div>
        </div>
    );
}
export default LaborCostTypeList;
