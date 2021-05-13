import { Formulation } from 'core/types/Formulation';
import { Link } from 'react-router-dom';
import './styles.scss';

type Props={
    formulation:Formulation;
    onRemove: (formulationId:number) =>void;
}
const FormulationCard = ({ formulation,onRemove }:Props) => {
    return (
        <div className="sector-card">
         
          <div className="sector-card-inf">
            <h5>{formulation.description}</h5>
          </div>

          <div className="sector-card-action">
                <Link
                    to={`/formulations/registrations/${formulation.id}`}
                    type="button"
                    className="btn btn-outline-secondary sector-card-action-btn sector-card-action-btn-edit">
                    EDITAR
                </Link>

                <button
                    type="button"
                    className="btn btn-outline-danger sector-card-action-btn"
                    onClick={() => onRemove(formulation.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default FormulationCard;
