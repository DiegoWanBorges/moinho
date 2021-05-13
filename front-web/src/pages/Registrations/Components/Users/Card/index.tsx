import { User } from 'core/types/User';
import { Link } from 'react-router-dom';
import './styles.scss';

type Props={
    user:User;
    onRemove: (productId:number) =>void;
}
const UserCard = ({ user,onRemove }:Props) => {
    return (
        <div className="user-card">
          <div className="user-card-inf">
            <h5>{user.name}</h5>
            <small>{user.email}</small>
          </div>

          <div className="user-card-action">
                <Link
                    to={`/registrations/users/${user.id}`}
                    type="button"
                    className="btn btn-outline-secondary user-card-action-btn user-card-action-btn-edit">
                    EDITAR
                </Link>

                <button
                    type="button"
                    className="btn btn-outline-danger user-card-action-btn"
                    onClick={() => onRemove(user.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default UserCard;
