import Filter from 'core/components/Filter';
import Pagination from 'core/components/Pagination';
import { SectorsResponse } from 'core/types/Employee';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';
import GroupCard from '../Card';
import './styles.scss';

function SectorList() {
    const [sectorsResponse, setSectorsResponse] = useState<SectorsResponse>();
    const [activePage, setActivePage] = useState(0);
    const [name, setName] = useState('');
  
    const getSectors = useCallback(() => {
        const params = {
            page: activePage,
            linesPerPage: 3,
            name:name,
            orderBy:"id",
            direction:"DESC"
        }
        makePrivateRequest({ url: '/sectors', params })
            .then(response => setSectorsResponse(response.data))
            .finally(() => {
    
            })
    }, [activePage,name])

    useEffect(() => {
        getSectors();
    }, [getSectors])

    const history = useHistory();


    const handCreate = () => {
        history.push("/registrations/sectors/new");
    }
    const onRemove = (sectorId: number) => {
        const confirm = window.confirm("Deseja excluir o setor selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/sectors/${sectorId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Setor excluido com sucesso!")
                    history.push('/registrations/sectors')
                    getSectors();
                })
                .catch(() => {
                    toast.error("Falha ao excluir setor!")
                    history.push('/registrations/sectors')
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
        <div className="group-list">
           <div className="group-list-add-filter">
               <button
                className="btn btn-primary btn-lg group-list-btn-add"
                onClick={handCreate}
               >
                   ADCIONAR
               </button>
               <Filter
                        name={name}
                        handleChangeName={handleChangeName}
                        clearFilters={clearFilters}
                        placeholder="Digite o nome do grupo"
                />
           </div>
           <div className="admin-list-container">
                {sectorsResponse?.content.map(sector => (
                    <GroupCard
                        sector={sector} key={sector.id}
                        onRemove={onRemove}
                    />
                ))}

                {sectorsResponse &&
                    <Pagination
                        totalPages={sectorsResponse?.totalPages}
                        onChange={page => setActivePage(page)}
                    />
                }
            </div>
        </div>
    );
}
export default SectorList;
