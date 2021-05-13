import { Unity } from 'core/types/Product';
import { Link } from 'react-router-dom';
import './styles.scss';

type Props={
    unity:Unity;
    onRemove: (unityId:string) =>void;
}
const UnityCard = ({ unity,onRemove }:Props) => {
    return (
        <div className="unity-card">
         
          <div className="unity-card-inf">
            <h5>{unity.id +" - "+ unity.description}</h5>
          </div>

          <div className="unity-card-action">
                <Link
                    to={`/registrations/units/${unity.id}`}
                    type="button"
                    className="btn btn-outline-secondary unity-card-action-btn unity-card-action-btn-edit">
                    EDITAR
                </Link>

                <button
                    type="button"
                    className="btn btn-outline-danger unity-card-action-btn"
                    onClick={() => onRemove(unity.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default UnityCard;
