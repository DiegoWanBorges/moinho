import { Link } from 'react-router-dom';
import './styles.scss';

function UserCard() {
    return (
        <div className="user-card">
          <div className="user-card-inf">
            <h5>Diego Wandrofski Borges</h5>
            <small>diego@gmail.com</small>
          </div>

          <div className="user-card-action">
                <Link
                    to={`registrations/users/1`}
                    type="button"
                    className="btn btn-outline-secondary user-card-action-btn user-card-action-btn-edit">
                    EDITAR
                </Link>

                <button
                    type="button"
                    className="btn btn-outline-danger user-card-action-btn"
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default UserCard;
