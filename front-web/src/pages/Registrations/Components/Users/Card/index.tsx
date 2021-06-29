import { User } from 'core/types/User';
import history from 'core/utils/history';

import './styles.scss';

type Props = {
    user: User;
    onRemove: (productId: number) => void;
}
const UserCard = ({ user, onRemove }: Props) => {
    const onEdit = () => {
        history.push(`/registrations/users/${user.id}`)
    }
    return (
        <div className="user-card">
            <div className="user-card-inf">
                <h5>{user.name}</h5>
                <small>{user.email}</small>
            </div>

            <div className="user-card-action">
                <button
                    type="button"
                    onClick={onEdit}
                    className="btn btn-outline-secondary user-card-action-btn user-card-action-btn-edit">
                    EDITAR
                </button>

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
