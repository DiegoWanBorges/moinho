import { Group } from 'core/types/Product';
import { Link } from 'react-router-dom';
import './styles.scss';

type Props={
    group:Group;
    onRemove: (groupId:number) =>void;
}
const GroupCard = ({ group,onRemove }:Props) => {
    return (
        <div className="group-card">
         
          <div className="group-card-inf">
            <h5>{group.name}</h5>
          </div>

          <div className="group-card-action">
                <Link
                    to={`/registrations/groups/${group.id}`}
                    type="button"
                    className="btn btn-outline-secondary group-card-action-btn group-card-action-btn-edit">
                    EDITAR
                </Link>

                <button
                    type="button"
                    className="btn btn-outline-danger group-card-action-btn"
                    onClick={() => onRemove(group.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default GroupCard;
