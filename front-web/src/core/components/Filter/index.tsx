import './styles.scss';
import { ReactComponent as SearchIcon } from 'core/assets/images/search.svg'



type Props = {
    name?: string;
    handleChangeName: (name: string) => void;
    linesPerPage?: number;
    handleChangeLinesPerPage: (name: number) => void;
    clearFilters: () => void;
    placeholder: string;
}


const Filter = ({ name, handleChangeName, clearFilters, placeholder, linesPerPage, handleChangeLinesPerPage }: Props) => {
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

            <div className="filters">
                <div className="filters-select">
                    <select
                        name="typeCostUsed"
                        className="filters-lines-select"
                        onChange={(e) => handleChangeLinesPerPage(parseInt(e.target.value))}
                        value={linesPerPage}
                    >
                        <option value="10">10</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                    </select>
                </div>
                <div className="filters-actions">
                    <button
                        className="btn btn-outline-secondary btn-filter-clean"
                        onClick={clearFilters}
                    >
                        LIMPAR FILTRO
                    </button>
                </div>
            </div>
        </div>
    )
}

export default Filter;