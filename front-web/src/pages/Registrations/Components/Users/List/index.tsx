import Pagination from 'core/components/Pagination';
import { UsersResponse } from 'core/types/Users';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';
import UserCard from '../Card';
import UserFilter from '../Filter';
import './styles.scss';

function UserList() {
    const [usersResponse, setUsersResponse] = useState<UsersResponse>();
    const [activePage, setActivePage] = useState(0);
    const [name, setName] = useState('');
    const getUsers = useCallback(() => {
        const params = {
            page: activePage,
            linesPerPage: 3,
            name:name,
            orderBy:"id",
            direction:"DESC"
        }
        makePrivateRequest({ url: '/users', params })
            .then(response => setUsersResponse(response.data))
            .finally(() => {
    
            })
    }, [activePage,name])

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
    const clearFilters = () => {
        setActivePage(0);
        setName('');
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
               <UserFilter
                        name={name}
                        handleChangeName={handleChangeName}
                        clearFilters={clearFilters}
                    />
           </div>
           <div className="admin-list-container">
                {usersResponse?.content.map(user => (
                    <UserCard
                        user={user} key={user.id}
                        onRemove={onRemove}
                    />
                ))}

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
