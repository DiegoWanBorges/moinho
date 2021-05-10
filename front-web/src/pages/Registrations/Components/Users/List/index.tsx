import UserCard from '../Card';
import './styles.scss';

function UserList() {
    return (
        <div className="user-list">
           <div className="user-list-add-filter">
               <button
                className="btn btn-primary btn-lg"
               >
                   ADCIONAR
               </button>
               <input
                className="input-base user-list-input-filter"
                placeholder="Pesquisar Usuário"
               />
           </div>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
           <UserCard/>
        </div>
    );
}
export default UserList;
