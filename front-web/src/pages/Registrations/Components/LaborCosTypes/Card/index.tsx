import { LaborCosType } from 'core/types/Payment';
import { Link } from 'react-router-dom';
import './styles.scss';

type Props={
    laborCosType:LaborCosType;
    onRemove: (laborCostTypeId:number) =>void;
}
const LaborCosTypeCard = ({ laborCosType,onRemove }:Props) => {
    return (
        <div className="laborCosType-card">
         
          <div className="laborCosType-card-inf">
            <h5>{laborCosType.name}</h5>
          </div>

          <div className="laborCosType-card-action">
                <Link
                    to={`/registrations/laborcosttypes/${laborCosType.id}`}
                    type="button"
                    className="btn btn-outline-secondary laborCosType-card-action-btn laborCosType-card-action-btn-edit">
                    EDITAR
                </Link>

                <button
                    type="button"
                    className="btn btn-outline-danger laborCosType-card-action-btn"
                    onClick={() => onRemove(laborCosType.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default LaborCosTypeCard;
