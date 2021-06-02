import Filter from 'core/components/Filter';
import Pagination from 'core/components/Pagination';
import { GroupsResponse } from 'core/types/Product';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';
import GroupCard from '../Card';
import './styles.scss';

function GroupList() {
    const [groupsResponse, setGroupsResponse] = useState<GroupsResponse>();
    const [activePage, setActivePage] = useState(0);
    const [name, setName] = useState('');
    const [linesPerPage,setLinesPerPage]=useState(10);
  
    const getGroups = useCallback(() => {
        const params = {
            page: activePage,
            linesPerPage: linesPerPage,
            name:name,
            orderBy:"id",
            direction:"DESC"
        }
        makePrivateRequest({ url: '/groups', params })
            .then(response => setGroupsResponse(response.data))
            .finally(() => {
    
            })
    }, [activePage,name,linesPerPage])

    useEffect(() => {
        getGroups();
    }, [getGroups])

    const history = useHistory();


    const handCreate = () => {
        history.push("/registrations/groups/new");
    }
    const onRemove = (groupId: number) => {
        const confirm = window.confirm("Deseja excluir o grupo selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/groups/${groupId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Grupo excluido com sucesso!")
                    history.push('/registrations/groups')
                    getGroups();
                })
                .catch(() => {
                    toast.error("Falha ao excluir grupo!")
                    history.push('/registrations/groups')
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
                        linesPerPage={linesPerPage}
                        handleChangeLinesPerPage={handleChangeLinesPerPage}
                        clearFilters={clearFilters}
                        placeholder="Digite o nome do grupo"
                />
           </div>
           <div className="admin-list-container">
                {groupsResponse?.content.map(group => (
                    <GroupCard
                        group={group} key={group.id}
                        onRemove={onRemove}
                    />
                ))}

                {groupsResponse &&
                    <Pagination
                        totalPages={groupsResponse?.totalPages}
                        onChange={page => setActivePage(page)}
                    />
                }
            </div>
        </div>
    );
}
export default GroupList;
