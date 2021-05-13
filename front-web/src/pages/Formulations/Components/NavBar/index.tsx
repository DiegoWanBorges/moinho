import { NavLink } from 'react-router-dom';
import './styles.scss'


const NavBarFormulation = () => (
    <nav className="nav-registration-container">
        <ul className="nav-registration-list">
            <li>
                <NavLink to="/formulations/registrations" className="nav-registration-item">Cadastro</NavLink>
            </li>
            <li>
                <NavLink to="/formulations/reports" className="nav-registration-item">Relatórios</NavLink>
            </li>
        </ul>
    </nav>
)

export default NavBarFormulation;