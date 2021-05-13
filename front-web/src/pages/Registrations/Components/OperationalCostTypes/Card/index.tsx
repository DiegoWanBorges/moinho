import { OperationalCostType } from 'core/types/OperationalCostType';
import { Link } from 'react-router-dom';
import './styles.scss';

type Props={
    operationalCostType:OperationalCostType;
    onRemove: (operationalCostTypeId:number) =>void;
}
const OperationalCostTypeCard = ({ operationalCostType,onRemove }:Props) => {
    return (
        <div className="operationalCostTypeCard-card">
          <div className="operationalCostTypeCard-card-inf">
            <h5>{operationalCostType.name}</h5>
          </div>

          <div className="operationalCostTypeCard-card-action">
                <Link
                    to={`/registrations/operationalcosttypes/${operationalCostType.id}`}
                    type="button"
                    className="btn btn-outline-secondary operationalCostTypeCard-card-action-btn operationalCostTypeCard-card-action-btn-edit">
                    EDITAR
                </Link>

                <button
                    type="button"
                    className="btn btn-outline-danger operationalCostTypeCard-card-action-btn"
                    onClick={() => onRemove(operationalCostType.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default OperationalCostTypeCard;
