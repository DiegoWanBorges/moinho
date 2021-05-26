import { NavLink } from 'react-router-dom';
import './styles.scss'


const NavBarStockMovemente = () => (
    <nav className="nav-stock-container">
        <ul className="nav-stock-list">
            <li>
                <NavLink to="/stock/movements" className="nav-stock-item">Movimentações</NavLink>
            </li>
        </ul>
    </nav>
)

export default NavBarStockMovemente;