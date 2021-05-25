import { Occurrence } from 'core/types/ProductionOrder';
import { Link } from 'react-router-dom';
import './styles.scss';

type Props={
    occurrence:Occurrence;
    onRemove: (occurrenceId:number) =>void;
}
const OccurrenceCard = ({ occurrence,onRemove }:Props) => {
    return (
        <div className="occurrence-card">
         
          <div className="occurrence-card-inf">
            <h5>{occurrence.name}</h5>
          </div>

          <div className="occurrence-card-action">
                <Link
                    to={`/registrations/occurrences/${occurrence.id}`}
                    type="button"
                    className="btn btn-outline-secondary occurrence-card-action-btn occurrence-card-action-btn-edit">
                    EDITAR
                </Link>

                <button
                    type="button"
                    className="btn btn-outline-danger occurrence-card-action-btn"
                    onClick={() => onRemove(occurrence.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default OccurrenceCard;
