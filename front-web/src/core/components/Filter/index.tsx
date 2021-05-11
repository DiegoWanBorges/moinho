import './styles.scss';
import { ReactComponent as SearchIcon } from 'core/assets/images/search.svg'


type Props ={
    name?: string;
    handleChangeName: (name:string) => void;
    clearFilters: () => void;
    placeholder: string;
}
 
const Filter = ({ name,handleChangeName,clearFilters,placeholder }:Props) => {
    
    return (
        <div className="card-base filters-container">
            <div className="input-search">
                <input
                    type="text"
                    className="form-control"
                    placeholder={placeholder}
                    onChange={event => handleChangeName(event.target.value)}
                    value={name}
                />
                <SearchIcon />
            </div>
            
            <button 
                    className="btn btn-outline-secondary btn-filter-clean"
                    onClick={clearFilters}
            >
                LIMPAR FILTRO
            </button>
        </div>
    )
}

export default Filter;