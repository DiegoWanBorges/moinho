import { NavLink } from 'react-router-dom';
import './styles.scss'


const NavBarProduction = () => (
    <nav className="nav-registration-container">
        <ul className="nav-registration-list">
            <li>
                <NavLink to="/productions/registrations" className="nav-registration-item">Cadastro</NavLink>
            </li>
            <li>
                <NavLink to="/productions/reports" className="nav-registration-item">Relatórios</NavLink>
            </li>
        </ul>
    </nav>
)

export default NavBarProduction;