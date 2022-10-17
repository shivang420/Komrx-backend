package com.komrz.trackxbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.komrz.trackxbackend.dto.TenantDetailsCreateDTO;
import com.komrz.trackxbackend.dto.UserCreateDTO;
import com.komrz.trackxbackend.dto.UserFetchDTO;
import com.komrz.trackxbackend.model.Role;
import com.komrz.trackxbackend.model.User;
import com.komrz.trackxbackend.repository.RoleRepository;
import com.komrz.trackxbackend.repository.UserRepository;
import com.komrz.trackxbackend.security.UserPrincipal;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username or email : " + username);
        }
        return new UserPrincipal(user);
    }

    public String createUser(UserCreateDTO userCreateDTO, String roleId, String tenantId, String username, String createdBy) {
    	User user = new User();
    	user.setName(userCreateDTO.getName());
    	user.setUsername(username);
    	user.setPassword(bCryptPasswordEncoder.encode(userCreateDTO.getPassword()));
    	user.setTenantId(tenantId);
    	user.setStatus('Y');
    	user.setCountryCode(userCreateDTO.getCountryCode());
    	user.setContactNo(userCreateDTO.getContactNo());
    	user.setCreatedBy(createdBy);
    	user.setCreateDate(new Date(System.currentTimeMillis()));
    	Set<Role> roles = new HashSet<Role>();
    	roles.add(roleRepository.getOne(roleId));
    	user.setRoles(roles);
    	return userRepository.save(user).getId();
    }
    
    // return true if exists
	// return false if not exists
	public boolean isExist(String userId, String tenantId) {
		List<User> user = userRepository.findByIdAndTenantId(userId, tenantId);
		if(user.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public String updateUser(String userId, UserCreateDTO userCreateDTO) {
		User user = userRepository.getOne(userId);
		user.setName(userCreateDTO.getName());
		user.setCountryCode(userCreateDTO.getCountryCode());
		user.setContactNo(userCreateDTO.getContactNo());
		user.setCreateDate(new Date(System.currentTimeMillis()));
		
		return userRepository.save(user).getId();	
	}
	
	public List<UserFetchDTO> fetchUsersByTenantId(String tenantId) {
		List<User> users = userRepository.findByTenantId(tenantId);
		List<UserFetchDTO> fetch = new ArrayList<>();
		ListIterator<User> listIterator = users.listIterator();
		while (listIterator.hasNext()) {
			fetch.add(getFetchDTO(listIterator.next()));
		}
		return fetch;
	}
	
	public UserFetchDTO fetchUser(User user) {
		UserFetchDTO fetch = new UserFetchDTO();
		fetch.setId(user.getId());
		fetch.setName(user.getName());
		fetch.setUsername(user.getUsername());
		List<String> roles = new ArrayList<>();
		Iterator<Role> iterator = user.getRoles().iterator();
		while (iterator.hasNext()) {
			roles.add(iterator.next().getName());
		}
		fetch.setRole(roles);
		return fetch;
	}
	
	private UserFetchDTO getFetchDTO(User user) {
		UserFetchDTO fetch = new UserFetchDTO();
		fetch.setId(user.getId());
		fetch.setName(user.getName());
		fetch.setUsername(user.getUsername());
		List<String> roles = new ArrayList<>();
		Iterator<Role> iterator = user.getRoles().iterator();
		while (iterator.hasNext()) {
			roles.add(iterator.next().getName());
		}
		fetch.setRole(roles);
		return fetch;
	}
	
	@Transactional
	public UserPrincipal fetchUserById(String userId) {
		User user = userRepository.findByUserId(userId);
		if (user == null) {
            throw new UsernameNotFoundException("User not found with Id : " + userId);
        }
		return new UserPrincipal(user);
	}

	public String createUser(TenantDetailsCreateDTO tenantDetailsCreateDTO, String roleId, String tenantId) {
		User user = new User();
		user.setName(tenantDetailsCreateDTO.getName());
    	user.setUsername(tenantDetailsCreateDTO.getUsername().toLowerCase());
    	user.setPassword(bCryptPasswordEncoder.encode(tenantDetailsCreateDTO.getPassword()));
    	user.setTenantId(tenantId);
    	user.setStatus('Y');
    	user.setCountryCode(tenantDetailsCreateDTO.getCountryCode());
    	user.setContactNo(tenantDetailsCreateDTO.getContactNo());
		user.setCreatedBy(null);
    	user.setCreateDate(new Date(System.currentTimeMillis()));
    	Set<Role> roles = new HashSet<Role>();
    	roles.add(roleRepository.getOne(roleId));
    	user.setRoles(roles);
    	return userRepository.save(user).getId();
	}
	
	public User getUserFromId(String userId) {
		return userRepository.getOne(userId);
	}

	public User getUserFromEmail(String email) {
		return userRepository.getUserByUsername(email);
	}

	public void changePassword(String userId, String password) {
		User user = userRepository.getOne(userId);
		if(user != null) {
			user.setPassword(bCryptPasswordEncoder.encode(password));
			userRepository.save(user);
		}
	}
}
