import Filter from 'core/components/Filter';
import Pagination from 'core/components/Pagination';
import { UnitysResponse } from 'core/types/Product';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';
import UnityCard from '../Card';
import './styles.scss';

function UnityList() {
    const [unitysResponse, setUnitysResponse] = useState<UnitysResponse>();
    const [activePage, setActivePage] = useState(0);
    const [name, setName] = useState('');
  
    const getunitys = useCallback(() => {
        const params = {
            page: activePage,
            linesPerPage: 3,
            description:name,
            orderBy:"id",
            direction:"DESC"
        }
        makePrivateRequest({ url: '/units', params })
            .then(response => setUnitysResponse(response.data))
            .finally(() => {
    
            })
    }, [activePage,name])

    useEffect(() => {
        getunitys();
    }, [getunitys])

    const history = useHistory();


    const handCreate = () => {
        history.push("/registrations/units/new");
    }
    const onRemove = (unityId: string) => {
        const confirm = window.confirm("Deseja excluir a unidade selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/units/${unityId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Unidade excluida com sucesso!")
                    history.push('/registrations/units')
                    getunitys();
                })
                .catch(() => {
                    toast.error("Falha ao excluir unidade!")
                    history.push('/registrations/units')
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
        <div className="unity-list">
           <div className="unity-list-add-filter">
               <button
                className="btn btn-primary btn-lg unity-list-btn-add"
                onClick={handCreate}
               >
                   ADCIONAR
               </button>
               <Filter
                        name={name}
                        handleChangeName={handleChangeName}
                        clearFilters={clearFilters}
                        placeholder="Digite o nome da unidade"
                />
           </div>
           <div className="admin-list-container">
                {unitysResponse?.content.map(unity => (
                    <UnityCard
                        unity={unity} key={unity.id}
                        onRemove={onRemove}
                    />
                ))}

                {unitysResponse &&
                    <Pagination
                        totalPages={unitysResponse?.totalPages}
                        onChange={page => setActivePage(page)}
                    />
                }
            </div>
        </div>
    );
}
export default UnityList;
