import { Sector } from 'core/types/Employee';
import { Link } from 'react-router-dom';
import './styles.scss';

type Props={
    sector:Sector;
    onRemove: (sectorId:number) =>void;
}
const SectorCard = ({ sector,onRemove }:Props) => {
    return (
        <div className="sector-card">
         
          <div className="sector-card-inf">
            <h5>{sector.name}</h5>
          </div>

          <div className="sector-card-action">
                <Link
                    to={`/registrations/sectors/${sector.id}`}
                    type="button"
                    className="btn btn-outline-secondary sector-card-action-btn sector-card-action-btn-edit">
                    EDITAR
                </Link>

                <button
                    type="button"
                    className="btn btn-outline-danger sector-card-action-btn"
                    onClick={() => onRemove(sector.id)}
                >
                    EXCLUIR
                    </button>
            </div>
          
        </div>
    );
}
export default SectorCard;
