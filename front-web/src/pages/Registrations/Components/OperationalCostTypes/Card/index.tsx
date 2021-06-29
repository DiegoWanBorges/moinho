import { OperationalCostType } from 'core/types/Payment';
import history from 'core/utils/history';
import './styles.scss';

type Props={
    operationalCostType:OperationalCostType;
    onRemove: (operationalCostTypeId:number) =>void;
}
const OperationalCostTypeCard = ({ operationalCostType,onRemove }:Props) => {
    const onEdit = () => {
        history.push(`/registrations/operationalcosttypes/${operationalCostType.id}`)
    }
    return (
        <div className="operationalCostTypeCard-card">
          <div className="operationalCostTypeCard-card-inf">
            <h5>{operationalCostType.name}</h5>
            <small>{operationalCostType.type}</small>
          </div>

          <div className="operationalCostTypeCard-card-action">
                <button
                    onClick={onEdit}
                    type="button"
                    className="btn btn-outline-secondary operationalCostTypeCard-card-action-btn operationalCostTypeCard-card-action-btn-edit">
                    EDITAR
                </button>

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
