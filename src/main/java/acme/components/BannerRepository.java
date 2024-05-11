/*
 * AdvertisementRepository.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.components;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.helpers.MomentHelper;
import acme.client.helpers.RandomHelper;
import acme.client.repositories.AbstractRepository;
import acme.entities.banner.Banner;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("select count(b) from Banner b where b.lastInstantiationMoment <= :currentDate and b.endOfInstantiation >= :currentDate")
	int countAdvertisements(Date currentDate);

	@Query("select b from Banner b where b.lastInstantiationMoment <= :currentDate and b.endOfInstantiation >= :currentDate")
	List<Banner> findManyBanners(PageRequest pageRequest, Date currentDate);

	default Banner findRandomBanner() {
		Banner result;
		int count, index;
		PageRequest page;
		List<Banner> list;
		Date currentDate;

		currentDate = MomentHelper.getCurrentMoment();
		count = this.countAdvertisements(currentDate);
		if (count == 0)
			result = null;
		else {
			index = RandomHelper.nextInt(0, count);

			page = PageRequest.of(index, 1, Sort.by(Direction.ASC, "id"));
			list = this.findManyBanners(page, currentDate);
			result = list.isEmpty() ? null : list.get(0);
		}

		return result;
	}

}
