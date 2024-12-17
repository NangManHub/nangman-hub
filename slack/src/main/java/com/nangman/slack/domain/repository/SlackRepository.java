package com.nangman.slack.domain.repository;

import com.nangman.slack.domain.entity.Slack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SlackRepository extends JpaRepository<Slack, UUID> {
}
