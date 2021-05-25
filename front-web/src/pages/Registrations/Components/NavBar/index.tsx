import { NavLink } from 'react-router-dom';
import './styles.scss'


const NavBarRegistration = () => (
    <nav className="nav-registration-container">
        <ul className="nav-registration-list">
            
            <li>
                <NavLink to="/registrations/parameters" className="nav-registration-item">Parâmetros</NavLink>
            </li>
            
            <li>
                <NavLink to="/registrations/users" className="nav-registration-item">Usuários</NavLink>
            </li>

            <li>
                <NavLink to="/registrations/groups" className="nav-registration-item">Grupos</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/units" className="nav-registration-item">Unidades</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/products" className="nav-registration-item">Produtos</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/sectors" className="nav-registration-item">Setores</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/employees" className="nav-registration-item">Funcionários</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/laborcosttypes" className="nav-registration-item">Custo funcionário</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/operationalcosttypes" className="nav-registration-item">Custo operacional</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/occurrences" className="nav-registration-item">Ocorrências</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/producedproductstatus" className="nav-registration-item">Status Pallet</NavLink>
            </li>
            
        </ul>

    </nav>
)

export default NavBarRegistration;