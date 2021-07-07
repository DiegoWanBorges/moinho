import Filter from 'core/components/Filter';
import Pagination from 'core/components/Pagination';
import { UsersResponse } from 'core/types/User';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';
import UserCard from '../Card';
import CardLoader from 'core/components/CardLoader';
import './styles.scss';

function UserList() {
    const [usersResponse, setUsersResponse] = useState<UsersResponse>();
    const [activePage, setActivePage] = useState(0);
    const [name, setName] = useState('');
    const [linesPerPage,setLinesPerPage]=useState(10);
    const [isLoading, setIsLoading] = useState(false);
    const getUsers = useCallback(() => {
        setIsLoading(true)
        const params = {
            page: activePage,
            linesPerPage: linesPerPage,
            name:name,
            orderBy:"id",
            direction:"DESC"
        }
        makePrivateRequest({ url: '/users', params })
            .then(response => setUsersResponse(response.data))
            .finally(() => {
                setIsLoading(false)
            })
    }, [activePage,name,linesPerPage])

    useEffect(() => {
        getUsers();
    }, [getUsers])

    const history = useHistory();


    const handCreate = () => {
        history.push("/registrations/users/new");
    }
    const onRemove = (userId: number) => {
        const confirm = window.confirm("Deseja excluir o usuário selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/users/${userId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Usuário excluido com sucesso!")
                    history.push('/registrations/users')
                    getUsers();
                })
                .catch(() => {
                    toast.error("Falha ao excluir usuário!")
                    history.push('/registrations/users')
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
        <div className="user-list">
           <div className="user-list-add-filter">
               <button
                className="btn btn-primary btn-lg user-list-btn-add"
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
                        placeholder="Digite o nome do usuário"
                />
           </div>
           <div className="admin-list-container">
                {isLoading ? <CardLoader/> :
                (usersResponse?.content.map(user => (
                        <UserCard
                            user={user} key={user.id}
                            onRemove={onRemove}
                        />
                )))}
                
                {usersResponse &&
                    <Pagination
                        totalPages={usersResponse?.totalPages}
                        onChange={page => setActivePage(page)}
                    />
                }
            </div>
        </div>
    );
}
export default UserList;
